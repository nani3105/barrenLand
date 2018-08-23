import org.junit.Test
import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.contains
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertThat
import static org.junit.Assert.assertThat

/**
 * User: Niranjan.kurra - Date: 8/22/18 7:53 AM
 */
class BarrenLandAnalysisTest extends Specification {

    @Unroll
    void calculateFertileLands() {
        when:
        BarrenLandAnalysis barrenLandAnalysis = new BarrenLandAnalysis(4, 5)
        List<Integer> fertileLands = barrenLandAnalysis.getFertileLands(barrenLands)

        then:
        expectedAreas == fertileLands

        where:
        barrenLands << [
             /*   [
                        [0, 292, 399, 307]
                ],
                [
                        [48, 192, 351, 207],
                        [48, 392, 351, 407],
                        [120, 52, 135, 547],
                        [260, 52, 275, 547]
                ],*/
                [
                        [0, 0, 2, 2]
                ]
        ]

        expectedAreas << [
                /*[116800, 116800],
                [22816, 192608],*/
                [239999]
        ]

    }
}
