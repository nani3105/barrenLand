package com.kurra.barrenland

import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: Niranjan.kurra - Date: 8/22/18 7:53 AM
 */
class BarrenLandAnalysisTest extends Specification {

    @Unroll
    void calculateFertileLands() {
        when:
        BarrenLandAnalysis barrenLandAnalysis = new BarrenLandAnalysis(400, 600)
        List<Integer> fertileLands = barrenLandAnalysis.getFertileLands(barrenLands)

        then:
        expectedAreas == fertileLands

        where:
        barrenLands << [
                [
                        [0, 292, 399, 307]
                ],
                [
                        [48, 192, 351, 207],
                        [48, 392, 351, 407],
                        [120, 52, 135, 547],
                        [260, 52, 275, 547]
                ],
                [
                        [0, 292, 399, 307],
                        [1, 20, 399, 599],
                        [100, 0, 399, 599],
                        [200, 199, 299, 256],
                        [5, 23, 58, 293]
                ]
        ]

        expectedAreas << [
                [116800, 116800],
                [22816, 192608],
                [292, 2272]
        ]

    }

    @Unroll
    void invalidInputs() {
        when:
        BarrenLandAnalysis barrenLandAnalysis = new BarrenLandAnalysis(400, 600)
        barrenLandAnalysis.getFertileLands(barrenLands)

        then:
        thrown(InputMismatchException)

        where:
        barrenLands << [
                [
                        [0, 0, 0, 0]
                ],
                [
                        [1000, 2323, 2349, 23444]
                ],
                [
                        [1, 1, 1, 2]
                ]
        ]

    }
}
