
package org.openo.commonsvc.protocolstack.test;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.commonsvc.protocolstack.moco.ProtocolStack;
import org.openo.sdno.testframework.checker.RegularExpChecker;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.topology.Topology;
import org.openo.sdno.testframework.util.file.FileUtils;

public class ITRestToNetConf extends TestManager {

    private ProtocolStack mocoServer = new ProtocolStack();

    private static final String TOPODATA_PATH = "src/integration-test/resources/testcase/topo";

    private Topology topo = new Topology(TOPODATA_PATH);

    @Before
    public void setup() throws ServiceException {

        mocoServer.start();

    }

    @After
    public void tearDown() throws ServiceException {

        mocoServer.stop();
    }

    @Test
    public void createsynctest() throws ServiceException {

        String uuid = "23-16";

        File createFile = new File("src/integration-test/resources/testcase/create.json");
        HttpRquestResponse createHttpObject =
                HttpModelUtils.praseHttpRquestResponse(FileUtils.readFromJson(createFile));
        HttpRequest createRequest = createHttpObject.getRequest();
        createRequest.setUri(PathReplace.replaceUuid("extSys-id", createHttpObject.getRequest().getUri(), uuid));
        send(createRequest, new RegularExpChecker(createHttpObject.getResponse()));

    }

    @Test
    public void createAsynctest() throws ServiceException {

        // Create Site to DC test case

        // String uuid = topo.getResourceUuid(ResourceType.CONTROLLER, "ACBranchController");

        String uuid = "23-16";

        File createFile = new File("src/integration-test/resources/testcase/createasync.json");
        HttpRquestResponse createHttpObject =
                HttpModelUtils.praseHttpRquestResponse(FileUtils.readFromJson(createFile));
        HttpRequest createRequest = createHttpObject.getRequest();
        createRequest.setUri(PathReplace.replaceUuid("extSys-id", createHttpObject.getRequest().getUri(), uuid));
        send(createRequest, new RegularExpChecker(createHttpObject.getResponse()));

    }

}
