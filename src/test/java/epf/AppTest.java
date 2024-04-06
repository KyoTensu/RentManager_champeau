package epf;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private ClientDao clientDao;
    @Mock
    private Client mockClient;
    @Mock
    private VehicleDao vehicleDao;
    @Mock
    private Vehicule mockVehicle;
    @Mock
    private Vehicule mockVehicleLegal;

    @Before
    public void setup(){
        mockVehicle = new Vehicule();
        mockVehicle.setConstructeur("Yamaha");
        mockVehicle.setModel("R6");
        mockVehicle.setNb_places(1);

        mockVehicleLegal = new Vehicule();
        mockVehicleLegal.setConstructeur("Citroen");
        mockVehicleLegal.setModel("C4 Picasso");
        mockVehicleLegal.setNb_places(7);
    }

    @Test
    public void Findall_should_fail_when_dao_throws_exception() throws DaoException{
        when(this.clientDao.findAll()).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

//    @Test
//    public void vehicleVerif_should_return_false_when_vehicle_have_too_less_seats() throws ServiceException{
//        when(vehicleService.verifVehicle(mockVehicle)).thenReturn(false);
//        boolean result = this.vehicleService.verifVehicle(mockVehicle);
//
//        assertFalse(result);
//    }

    @Test
    public void vehicle_findById_should_return_vehicle() throws ServiceException, DaoException{
        when(vehicleDao.findById(1L)).thenReturn(mockVehicleLegal);
        Vehicule foundVehicle = vehicleService.findById(1L);
        assertNotNull(foundVehicle);
        assertEquals(mockVehicleLegal.getId(), foundVehicle.getId());
        assertEquals(mockVehicleLegal.getConstructeur(), foundVehicle.getConstructeur());
        assertEquals(mockVehicleLegal.getModel(), foundVehicle.getModel());
        assertEquals(mockVehicleLegal.getNb_places(), foundVehicle.getNb_places());
    }

    @Test
    public void vehicle_create_should_return_id() throws DaoException, ServiceException{
        when(vehicleDao.create(mockVehicleLegal)).thenReturn(1L);
        long vehicleId = vehicleService.create(mockVehicleLegal);

        assertEquals(1L, vehicleId);
    }

    @Test
    public void client_count_should_return_integer() throws DaoException, ServiceException{
        when(vehicleDao.countVehicles()).thenReturn(1);
        int result = vehicleService.countVehicles();

        assertEquals(1, result);
    }
}
