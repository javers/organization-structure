package org.javers.organization.structure.infrastructure;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import org.javers.core.Javers;
import org.javers.organization.structure.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.javers.organization.structure.domain.Position.*;

/**
 * @author bartosz walacik
 */
@Component
public class DataInitializer {

    Person frodo;
    Person bilbo;
    Person sam;
    Person merry;
    Person lucy;
    Person eva;
    Person charlie;
    Person kaz;
    Person stef;
    Person bob;

    Person stanMarsh;
    Person kyleBroflovski;
    Person ericCartman;
    Person kennyMcCormick;

    List<Person> persons;

    Employee eFrodo;
    Employee eBilbo;
    Employee eSam;
    Employee eMerry;
    Employee eLucy;
    Employee eEva;
    Employee eCharlie;
    Employee eKaz;
    Employee eStef;
    Employee eBob;

    Employee eStanMarsh;
    Employee eKyleBroflovski;
    Employee eEricCartman;
    Employee eKennyMcCormick;


    public DataInitializer() {
        frodo = new Person("frodo", "Frodo", "Baggins", Sex.MALE).assignPosition(DEVELOPER, 9_000);
        bilbo = new Person("bilbo", "Bilbo", "Baggins", Sex.MALE).assignPosition(SCRUM_MASTER, 10_000);
        sam = new Person("sam", "Sam", "Gamgee", Sex.MALE).assignPosition(DEVELOPER, 11_000);
        merry = new Person("merry", "Meriadoc", "Brandybuck", Sex.MALE).assignPosition(DEVELOPER, 12_000);
        lucy = new Person("lucy", "Lucy","Valinor", Sex.FEMALE).assignPosition(TEAM_LEAD, 13_000);
        eva = new Person("eva", "Eva","Celebrimbor", Sex.FEMALE).assignPosition(TEAM_LEAD, 14_000);
        charlie = new Person("charlie","Charlie","Big", Sex.MALE).assignPosition(TEAM_LEAD, 15_000);
        kaz = new Person("kaz", "Mad","Kaz", Sex.MALE).assignPosition(IT_MANAGER, 16_000);
        stef = new Person("stef", "Crazy","Stefan", Sex.MALE).assignPosition(IT_MANAGER, 17_000);
        bob = new Person("bob", "Uncle","Bob", Sex.MALE).assignPosition(CTO, 20_000);

        stanMarsh = new Person("stan_m", "Stan", "Marsh", Sex.MALE).assignPosition(DEVELOPER, 9_000);
        kyleBroflovski = new Person("kyle_b", "Kyle", "Broflovski", Sex.MALE).assignPosition(DEVELOPER, 9_000);
        ericCartman = new Person("eric_c", "Eric", "Cartman", Sex.MALE).assignPosition(DEVELOPER, 9_000);
        kennyMcCormick = new Person("kenny_m", "Kenny", "McCormick", Sex.MALE).assignPosition(DEVELOPER, 9_000);

        persons = Lists.newArrayList(frodo, bilbo, sam, merry, lucy, eva, charlie, kaz, stef, bob, stanMarsh, kyleBroflovski, ericCartman, kennyMcCormick);

        eFrodo = frodo.toEmployee();
        eBilbo = bilbo.toEmployee();
        eSam = sam.toEmployee();
        eMerry = merry.toEmployee();
        eLucy = lucy.toEmployee();
        eEva = eva.toEmployee();
        eCharlie = charlie.toEmployee();
        eKaz = kaz.toEmployee();
        eStef = stef.toEmployee();
        eBob = bob.toEmployee();

        eStanMarsh = stanMarsh.toEmployee();
        eKyleBroflovski = kyleBroflovski.toEmployee();
        eEricCartman = ericCartman.toEmployee();
        eKennyMcCormick = kennyMcCormick.toEmployee();
    }

    @Autowired
    private HierarchyRepository hierarchyRepository;
    
    @Autowired
    private PersonRepository mongoPersonRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Javers javers;

    @Value("${datainitializer.populate}")
    private boolean populate;

    public void populate() {
        if (populate) {

            mongoTemplate.getCollection(Person.class.getSimpleName()).drop();
            mongoTemplate.getCollection(Hierarchy.class.getSimpleName()).drop();
            mongoTemplate.getCollection("jv_head_id").drop();
            mongoTemplate.getCollection("jv_snapshots").drop();

            persons.forEach(p -> {
                mongoPersonRepository.save(p);
                javers.commit("author", p);
            });


            eBob.addSubordinate(eLucy);
            eBob.addSubordinate(eKaz);
            Hierarchy hier_2013 = new Hierarchy(eBob, "Hier_2013");
            hierarchyRepository.save(hier_2013);


            Hierarchy hier_2014 = new Hierarchy(new DataInitializer().createBobTree(), "Hier_2014");
            hierarchyRepository.save(hier_2014);
            javers.commit("author", hier_2014);


            Employee bobNew = new DataInitializer().createBobTree();
            Employee kazNew = bobNew.getSubordinate("kaz");
            Employee stefNew = bobNew.getSubordinate("stef");
            kazNew.addSubordinate(stefNew.getSubordinate("charlie"));

            eEricCartman.addSubordinate(eStanMarsh);
            eEricCartman.addSubordinate(eKyleBroflovski);
            eEricCartman.addSubordinate(eKennyMcCormick);

            bobNew.addSubordinate(eEricCartman);


            Hierarchy hier_2015 = new Hierarchy(bobNew, "Hier_2015");
            hierarchyRepository.save(hier_2015);
            javers.commit("author", hier_2015);
        }
    }

    public  Employee createBobTree() {
        eLucy.addSubordinates(Lists.newArrayList(eFrodo, eBilbo));
        eEva.addSubordinates(Lists.newArrayList(eSam, eMerry));
        eKaz.addSubordinates(Lists.newArrayList(eLucy, eEva));
        eStef.addSubordinate(eCharlie);
        eBob.addSubordinates(Lists.newArrayList(eKaz, eStef));
        return eBob;
    }
}
