package com.boot;

import com.boot.controller.ShipwreckController;
import com.boot.model.Shipwreck;
import com.boot.repository.ShipwreckRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ShipwreckControllerTest{
    @InjectMocks ShipwreckController sc;

    @Mock
    ShipwreckRepository shipwreckRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShipwreckGet(){
       // this will not work because the internla injection of repository we have to use mockito to stub this functionality.
       // ShipwreckController sc = new ShipwreckController();
        Shipwreck sw = new Shipwreck();        //We will create a fake object to return with as the mock.
        sw.setId(1L);
        // set the stub
        when(shipwreckRepository.getOne(1L)).thenReturn(sw);

        Shipwreck wreck = sc.get(1L);
        verify(shipwreckRepository).getOne(1L);       // Verify that getOne is called inside the controller in shipwreckRepository object
        assertEquals(1L,wreck.getId().longValue());
    }

    @Test
    public void testList(){
        List<Shipwreck> mockList = new ArrayList<>();
        mockList.add(new Shipwreck());
        mockList.add(new Shipwreck());
        when(shipwreckRepository.findAll()).thenReturn(mockList);
        List<Shipwreck> list = sc.list();
        assertEquals(mockList,list);
    }

    @Test
    public void testUpdate(){
        Shipwreck currentSw = new Shipwreck();        //Current mock object
        currentSw.setId(1L);
        currentSw.setDescription("current value");
        Shipwreck updatedSw = new Shipwreck();        //Updated object
        updatedSw.setId(1L);
        updatedSw.setDescription("updated value");
        when(shipwreckRepository.getOne(1L)).thenReturn(currentSw);
        when(shipwreckRepository.saveAndFlush(currentSw)).thenReturn(updatedSw);

        Shipwreck resultSw = sc.update(1L,updatedSw);
        assertEquals(updatedSw.getDescription(),resultSw.getDescription());
    }

}
