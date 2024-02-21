package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {
    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_loggetInn() {

        // arrange
        List<Kunde> kunder = new ArrayList<>();

        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");
        Kunde kunde2 = new Kunde("02020220634",
                "Arild", "Hansen", "Askerveien 23", "3270",
                "Asker", "66668888", "HalloHallo");
        kunder.add(kunde1);
        kunder.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKunder()).thenReturn(kunder);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kunder, resultat);
    }

    @Test
    public void hentAlleKunde_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void lagreKunde_loggetInn() {

        // arrange
        Kunde kunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKunde(kunde)).thenReturn(String.valueOf(kunde));

        // act
        String resultat = adminKundeController.lagreKunde(kunde);

        // assert
        assertEquals(String.valueOf(kunde), resultat);
    }

    @Test
    public void lagreKunde_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.lagreKunde(new Kunde());

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endreKunde_loggetInn() {

        // arrange
        Kunde kunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(kunde)).thenReturn(String.valueOf(kunde));

        // act
        String resultat = adminKundeController.endre(kunde);

        // assert
        assertEquals(String.valueOf(kunde), resultat);
    }

    @Test
    public void endreKunde_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.endre(new Kunde());

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slettKunde_loggetInn() {

        // arrange
        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKunde(anyString())).thenReturn("01010110523");

        // act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals(personnummer, resultat);
    }

    @Test
    public void slettKunde_ikkeLoggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.slett("");

        // assert
        assertEquals("Ikke logget inn", resultat);
    }
}
