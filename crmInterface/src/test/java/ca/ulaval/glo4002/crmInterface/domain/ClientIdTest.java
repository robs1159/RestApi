package ca.ulaval.glo4002.crmInterface.domain;

import ca.ulaval.glo4002.crmInterface.domain.ClientId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientIdTest {

    private static final long VALID_CLIENT_ID = 1;
    private static final String VALID_STRING_CLIENT_ID = "1";

    private ClientId clientId;

    @Before
    public void setup() {
        clientId = new ClientId(VALID_CLIENT_ID);
    }

    @Test
    public void givenClientId_whenClientIdIsCreated_thenAssignThisLongAsClientId() {
        Assert.assertEquals(VALID_CLIENT_ID, clientId.getClientId());
    }

    @Test
    public void givenClientId_whenConvertClientIdToString_thenReturnClientIdToString() {
        Assert.assertEquals(VALID_STRING_CLIENT_ID, clientId.toString());
    }
}
