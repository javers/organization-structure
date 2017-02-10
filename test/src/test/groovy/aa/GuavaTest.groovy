package aa

import org.javers.core.Javers
import org.javers.core.JaversBuilder
import org.javers.repository.jql.QueryBuilder
import spock.lang.Specification

/**
 * @author bartosz.walacik
 */
class GuavaTest extends Specification {


    class S {
        Set m;
    }

    def "should  "(){
      given:
      Javers j = JaversBuilder.javers().build()

      when:
      def s = new S(m: new HashSet())
      s.m.add("S")

      def s2 = new S(m: new HashSet())
      s.m.add("S2")

      j.commit("a", s)
      j.commit("a", s2)


       then:
       j.findSnapshots(QueryBuilder.anyDomainObject().build()).size() == 2
    }
}
