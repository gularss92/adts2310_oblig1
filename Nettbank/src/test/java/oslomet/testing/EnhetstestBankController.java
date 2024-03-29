package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentTransaksjoner_LoggetInn() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 15000, "2023-02-02",
                "Husleie februar 2023", "", "12345678901");
        Transaksjon transaksjon2 = new Transaksjon(1, "01010110523", 15000, "2023-04-04",
                "Husleie april 2023", "", "12345678901");
        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentTransaksjoner("105010123456", "2023-02-02",
                "2023-02-02")).thenReturn(konto);

        // act
        Konto resultat = bankController.hentTransaksjoner("105010123456", "2023-02-02",
                "2023-02-02");

        // assert
        assertEquals(konto, resultat);
    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner(null,"2023-02-02","2023-02-02");

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn() {
        // arrange
        Transaksjon transaksjon = new Transaksjon(1, "01010110523", 15000, "2023-04-04",
                "Husleie april 2023", "", "12345678901");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(transaksjon)).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(transaksjon);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        Transaksjon transaksjon = new Transaksjon(1, "01010110523", 15000, "2023-04-04",
                "Husleie april 2023", "", "12345678901");

        // act
        String resultat = bankController.registrerBetaling(transaksjon);

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 15000, "2023-02-02",
                "Husleie februar 2023", "", "12345678901");
        Transaksjon transaksjon2 = new Transaksjon(2, "01010110523", 15000, "2023-04-04",
                "Husleie april 2023", "", "12345678901");
        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_LoggetInn() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 15000, "2023-02-02",
                "Husleie februar 2023", "", "12345678901");
        transaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(1)).thenReturn("OK");
        when(repository.hentBetalinger("01010110523")).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertEquals(transaksjoner, resultat);
    }

    @Test
    public void utforBetaling_LoggetInn_IDFeil() {
        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.utforBetaling(anyInt())).thenReturn("Feil");

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(3);

        // assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(1);

        // assert
        assertNull(resultat);
    }

    @Test
    public void endreKundeInfo_LoggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKundeInfo_IkkeLoggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }
}

