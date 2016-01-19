package org.javers.organization.structure.ui;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Hierarchy;

import java.util.List;

public class OrganizationTree extends CustomComponent {

    private final VerticalLayout verticalLayout = new VerticalLayout();
    private final ComboBox comboBox = new ComboBox();
    private final Tree tree = new Tree();
    private final CheckBox checkBoxEdit = new CheckBox("Edit", false);
    private final HierarchicalContainer container = new HierarchicalContainer();

    private List<Hierarchy> hierarchies;
    private Hierarchy selected;

    public OrganizationTree(Controller controller) {
        tree.setEnabled(false);

        Panel panel = new Panel();
        panel.setHeight("650px");
        verticalLayout.setMargin(true);
        panel.setContent(verticalLayout);
        setCompositionRoot(panel);
        setSizeFull();
        tree.setSizeFull();
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(comboBox);
        hl.addComponent(checkBoxEdit);
        hl.setComponentAlignment(checkBoxEdit, Alignment.MIDDLE_RIGHT);
        hl.setWidth("100%");

        checkBoxEdit.addValueChangeListener(l -> {
            tree.setEnabled(checkBoxEdit.getValue());
        });

        verticalLayout.addComponent(hl);
        verticalLayout.addComponent(tree);

        hierarchies = controller.getHierarchyList();
        selected = hierarchies.get(0);
        comboBox.addItems(hierarchies);
        comboBox.select(selected);
        comboBox.setImmediate(true);
        comboBox.setNullSelectionAllowed(false);
        comboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                selected = (Hierarchy) comboBox.getValue();
                container.removeAllItems();
                populateContainer(selected);
            }
        });

        hierarchies.forEach(i -> comboBox.setItemCaption(i, i.getHierarchyName()));
        container.addContainerProperty("login", String.class, null);
        tree.setContainerDataSource(container);
        tree.setItemCaptionPropertyId("login");
        tree.setDragMode(Tree.TreeDragMode.NODE);
        tree.setDropHandler(new TreeSortDropHandler(tree));

        tree.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Employee e = (Employee) tree.getValue();
                if (e!=null) {
                controller.employeeSelected(e);

                }

            }
        });

        populateContainer(selected);
    }

    private void populateContainer(Hierarchy first) {
        Employee root = first.getRoot();

        Item item = container.addItem(root);
        item.getItemProperty("login").setValue(root.getLogin());
        addSubordinates(root);
        tree.rootItemIds().forEach(i -> tree.expandItemsRecursively(i));
    }

    private void addSubordinates(Employee root) {
        for (Employee e : root.getAllEmployees()) {
            Item item = container.addItem(e);
            item.getItemProperty("login").setValue(e.getLogin());
            container.setParent(e, root);
            addSubordinates(e);
        }
    }

    //MAGIC
    private static class TreeSortDropHandler implements DropHandler {
        private final Tree tree;

        public TreeSortDropHandler(final Tree tree) {
            this.tree = tree;
        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            // Alternatively, could use the following criteria to eliminate some
            // checks in drop():
            // new And(IsDataBound.get(), new DragSourceIs(tree));
            return AcceptAll.get();
        }

        @Override
        public void drop(final DragAndDropEvent dropEvent) {
            final Transferable t = dropEvent.getTransferable();
            if (t.getSourceComponent() != tree || !(t instanceof DataBoundTransferable)) {
                return;
            }
            final Tree.TreeTargetDetails dropData = ((Tree.TreeTargetDetails) dropEvent.getTargetDetails());
            final Object sourceItemId = ((DataBoundTransferable) t).getItemId();
            final Object targetItemId = dropData.getItemIdOver();
            final VerticalDropLocation location = dropData.getDropLocation();


            moveNode(sourceItemId, targetItemId, location);
        }

        /**
         * Move a node within a tree onto, above or below another node depending
         * on the drop location.
         *
         * @param sourceItemId id of the item to move
         * @param targetItemId id of the item onto which the source node should be moved
         * @param location     VerticalDropLocation indicating where the source node was
         *                     dropped relative to the target node
         */
        private void moveNode(final Object sourceItemId,
                              final Object targetItemId, final VerticalDropLocation location) {
            final HierarchicalContainer container = (HierarchicalContainer) tree
                    .getContainerDataSource();

            // Sorting goes as
            // - If dropped ON a node, we preppend it as a child
            // - If dropped on the TOP part of a node, we move/add it before
            // the node
            // - If dropped on the BOTTOM part of a node, we move/add it
            // after the node if it has no children, or prepend it as a child if
            // it has children

            if (location == VerticalDropLocation.MIDDLE) {
                if (container.setParent(sourceItemId, targetItemId)
                        && container.hasChildren(targetItemId)) {
                    // move first in the container
                    container.moveAfterSibling(sourceItemId, null);
                }
            } else if (location == VerticalDropLocation.TOP) {
                final Object parentId = container.getParent(targetItemId);
                if (container.setParent(sourceItemId, parentId)) {
                    // reorder only the two items, moving source above target
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    container.moveAfterSibling(targetItemId, sourceItemId);
                }
            } else if (location == VerticalDropLocation.BOTTOM) {
                if (container.hasChildren(targetItemId)) {
                    moveNode(sourceItemId, targetItemId,
                            VerticalDropLocation.MIDDLE);
                } else {
                    final Object parentId = container.getParent(targetItemId);
                    if (container.setParent(sourceItemId, parentId)) {
                        container.moveAfterSibling(sourceItemId, targetItemId);
                    }
                }
            }
        }
    }
}
