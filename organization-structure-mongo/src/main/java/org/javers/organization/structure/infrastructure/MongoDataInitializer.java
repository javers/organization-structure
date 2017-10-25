package org.javers.organization.structure.infrastructure;

import com.google.common.collect.Lists;
import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Hierarchy;
import org.javers.organization.structure.domain.HierarchyRepository;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.domain.PersonRepository;
import org.javers.organization.structure.domain.Position;
import org.javers.organization.structure.domain.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author bartosz walacik
 */
@Component
public class MongoDataInitializer {

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

    @Autowired
    private HierarchyRepository hierarchyRepository;

    @Autowired
    private PersonRepository personRepository;

    private MongoTemplate mongoTemplate;

    private boolean populate = true;


    public MongoDataInitializer(MongoTemplate mongoTemplate,
                                PersonRepository personRepository,
                                HierarchyRepository hierarchyRepository,
                                boolean populate) {
        this.mongoTemplate = mongoTemplate;
        this.personRepository = personRepository;
        this.hierarchyRepository = hierarchyRepository;
        this.populate = populate;


        frodo = new Person(0, "Frodo", "Baggins", Sex.MALE).assignPosition(Position.DEVELOPER, 9_000);
        bilbo = new Person(1, "Bilbo", "Baggins", Sex.MALE).assignPosition(Position.SCRUM_MASTER, 10_000);
        sam = new Person(2, "Sam", "Gamgee", Sex.MALE).assignPosition(Position.DEVELOPER, 11_000);
        merry = new Person(3, "Meriadoc", "Brandybuck", Sex.MALE).assignPosition(Position.DEVELOPER, 12_000);
        lucy = new Person(4, "Lucy", "Valinor", Sex.FEMALE).assignPosition(Position.TEAM_LEAD, 13_000);
        eva = new Person(5, "Eva", "Celebrimbor", Sex.FEMALE).assignPosition(Position.TEAM_LEAD, 14_000);
        charlie = new Person(6, "Charlie", "Big", Sex.MALE).assignPosition(Position.TEAM_LEAD, 15_000);
        kaz = new Person(7, "Mad", "Kaz", Sex.MALE).assignPosition(Position.IT_MANAGER, 16_000);
        stef = new Person(8, "Crazy", "Stefan", Sex.MALE).assignPosition(Position.IT_MANAGER, 17_000);
        bob = new Person(9, "Uncle", "Bob", Sex.MALE).assignPosition(Position.CTO, 20_000);

        stanMarsh = new Person(10, "Stan", "Marsh", Sex.MALE).assignPosition(Position.DEVELOPER, 9_000);
        kyleBroflovski = new Person(11, "Kyle", "Broflovski", Sex.MALE).assignPosition(Position.DEVELOPER, 9_000);
        ericCartman = new Person(12, "Eric", "Cartman", Sex.MALE).assignPosition(Position.DEVELOPER, 9_000);
        kennyMcCormick = new Person(13, "Kenny", "McCormick", Sex.MALE).assignPosition(Position.DEVELOPER, 9_000);

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
        populate();
    }

    @Autowired
    public MongoDataInitializer(MongoTemplate mongoTemplate,
                                PersonRepository personRepository,
                                HierarchyRepository hierarchyRepository) {
        this(mongoTemplate, personRepository, hierarchyRepository, true);
    }

    public void populate() {
        if (populate) {

            mongoTemplate.dropCollection(Person.class);
            mongoTemplate.dropCollection(Hierarchy.class);
            mongoTemplate.dropCollection("jv_head_id");
            mongoTemplate.dropCollection("jv_snapshots");

            persons.forEach(p -> {
                personRepository.save(p);
            });


            eBob.addSubordinate(eLucy);
            eBob.addSubordinate(eKaz);
            Hierarchy hier_2013 = new Hierarchy("Hier_2013", Lists.newArrayList(eBob, eLucy, eKaz));
            hierarchyRepository.save(hier_2013);

            Hierarchy hier_2014 = new Hierarchy("Hier_2014", new MongoDataInitializer(mongoTemplate, personRepository, hierarchyRepository, false).createBobTree());
            hierarchyRepository.save(hier_2014);

            List<Employee> employees = new MongoDataInitializer(mongoTemplate, personRepository, hierarchyRepository, false).createBobTree();
            eKaz.addSubordinate(eCharlie);

            eEricCartman.addSubordinate(eStanMarsh);
            eEricCartman.addSubordinate(eKyleBroflovski);
            eEricCartman.addSubordinate(eKennyMcCormick);

            eBob.addSubordinate(eEricCartman);

            Hierarchy hier_2015 = new Hierarchy("Hier_2015", employees);
            hierarchyRepository.save(hier_2015);
        }
    }

    public List<Employee> createBobTree() {
        eLucy.addSubordinates(Lists.newArrayList(eFrodo, eBilbo));
        eEva.addSubordinates(Lists.newArrayList(eSam, eMerry));
        eKaz.addSubordinates(Lists.newArrayList(eLucy, eEva));
        eStef.addSubordinate(eCharlie);
        eBob.addSubordinates(Lists.newArrayList(eKaz, eStef));
        return Lists.newArrayList(eBob, eLucy, eFrodo, eBilbo, eEva, eSam, eMerry, eStef, eKaz, eStef);
    }
}
