package epf;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit test for simple App.
 */
@RunWith(MockitoJUnitRunner.class)
public class    AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    @Test
    public void Findall_should_fail_when_dao_throws_exception() throws DaoException{
        when(this.clientDao.findAll()).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    //faire d'autres tests
}
