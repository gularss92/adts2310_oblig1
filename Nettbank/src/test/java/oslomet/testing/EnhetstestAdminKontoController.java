package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_loggetInn() {

        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentAlleKonti()).thenReturn(konti);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentAlle_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_loggetInn() {

        // arrange
        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerKonto(konto)).thenReturn(String.valueOf(konto));

        // act
        String resultat = adminKontoController.registrerKonto(konto);

        // assert
        assertEquals(String.valueOf(konto),resultat);
    }

    @Test
    public void registrerKonto_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.registrerKonto(new Konto());

        // assert
        assertEquals("Ikke innlogget",resultat);
    }

    @Test
    public void endreKonto_loggetInn() {

        // arrange
        Konto konto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.endreKonto(konto)).thenReturn(String.valueOf(konto));
        // act
        String resultat = adminKontoController.endreKonto(konto);

        // assert
        assertEquals(String.valueOf(konto),resultat);
    }

    @Test
    public void endreKonto_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.endreKonto(new Konto());

        // assert
        assertEquals("Ikke innlogget",resultat);
    }

    @Test
    public void slettKonto_loggetInn() {

        // arrange
        String kontonummer = "01010110523" ;

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKonto(kontonummer)).thenReturn("01010110523");
        // act
        String resultat = adminKontoController.slettKonto(kontonummer);

        // assert
        assertEquals(kontonummer,resultat);
    }

    @Test
    public void slettKonto_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.slettKonto("");

        // assert
        assertEquals("Ikke innlogget",resultat);
    }
}

