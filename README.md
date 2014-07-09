BulkData Loader for <a href="http://openstates.org">OpenStates</a> BulkData.
=========
This program is a BulkData load library for the <a href="http://openstates.org">OpenStates.org Website.</a>
The <a href="http://openstates.org/downloads/">OpenStates Download</a> site has 
additional information and terms of use.
The typical user will typically use the classes in the org.openstates.bulkdata package.
Java version 1.6 at a minimum is required. In addition, the org.fasterxml.jackson json jar is required and the org.openstates.openstates-client libraries are required.

Version 1.0.1 has been put in <a href="http://search.maven.org/#browse">The Central Repository</a>.

If you are using maven, then you can add this to your project .pom file to include this client:

    <dependency>
        <groupId>org.openstates</groupId>
        <artifactId>openstates-bulk</artifactId>
        <version>1.0.1</version>
    </dependency>

Otherwise, you can get everything from the <a href="https://github.com/karlnicholas/openstates-bulk/releases">release</a> tab above or go to <a href="http://search.maven.org/#browse">The Central Repository</a> website and download from there.

Examples
========
Examples are in the <a href="https://github.com/karlnicholas/openstates-bulk/tree/master/src/main/java/examples">examples</a> directory.

    examples/LoadState.java

