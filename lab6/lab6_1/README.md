The euromillions app passes the default SonarQube quality gate, which complies with Clean as you Code methodology and is only passed if all the following metrics are met: % coverage > 80%, % duplicated lines <= 3%, maintainability rating is A, reliability rating is A (no bugs), security hotspots reviewed is 100%, security rating is A.

No bugs.

No vulnerabilities.

1 security hotspot in line "static Random generator = new Random();" of Dip class. <br />
Description: make sure that using this pseudorandom number generator is safe here.

26 code smells. <br />
5 major ones. <br />
3 of them are on log.info() calls, SonarQube says "Invoke method(s) only conditionally". <br />
The other 2 are in for loops: "Refactor the code in order to not assign to this loop counter from within the loop body". <br />


