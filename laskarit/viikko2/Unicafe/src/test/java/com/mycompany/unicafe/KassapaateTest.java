/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jmammela
 */
public class KassapaateTest {
    
    Kassapaate paate = new Kassapaate();
    
    @Before
    public void setUp() {
        
    }
    
    @Test
    public void rahatKassassaOikein() {
        assertEquals(paate.kassassaRahaa(), 100000);
    }
    
    @Test
    public void lounaitaAlussaNolla() {
        assertEquals(paate.maukkaitaLounaitaMyyty() + paate.edullisiaLounaitaMyyty(), 0);
    }
    
    @Test
    public void edullinenLisaaKassaaOikein() {
        paate.syoEdullisesti(240);
        assertEquals(paate.kassassaRahaa(), 100240);
    }
    
    @Test
    public void maukasLisaaKassaaOikein() {
        paate.syoMaukkaasti(400);
        assertEquals(paate.kassassaRahaa(), 100400);
    }
    
    @Test
    public void edullinenVaihtorahaOikein() {
        assertEquals(paate.syoEdullisesti(250), 10);
    }
    
    @Test
    public void maukasVaihtorahaOikein() {
        assertEquals(paate.syoMaukkaasti(410), 10);
    }
    
    @Test
    public void edullistenLounaidenMaaraKasvaa() {
        paate.syoEdullisesti(240);
        assertEquals(paate.edullisiaLounaitaMyyty(), 1);
    }
    
    @Test
    public void maukkaidenLounaidenMaaraKasvaa() {
        paate.syoMaukkaasti(400);
        assertEquals(paate.maukkaitaLounaitaMyyty(), 1);
    }
    
    @Test
    public void riittamatonMaksuPalautetaanEdullisilla() {
        int vaihto = paate.syoEdullisesti(10);
        assertEquals(vaihto, 10);
    }
    
    @Test
    public void riittamatonMaksuPalautetaanMaukkailla() {
        int vaihto = paate.syoMaukkaasti(10);
        assertEquals(vaihto, 10);
    }
    
    @Test
    public void riittamatonEiKasvataEdullisilla() {
        paate.syoEdullisesti(10);
        assertEquals(paate.kassassaRahaa(), 100000);
    }
    
    @Test
    public void riittamatonEiKasvataMaukkailla() {
        paate.syoMaukkaasti(10);
        assertEquals(paate.kassassaRahaa(), 100000);
    }
    
    @Test
    public void riittamatonEiKasvata() {
        paate.syoEdullisesti(10);
        paate.syoMaukkaasti(10);
        
        assertEquals(paate.edullisiaLounaitaMyyty() + paate.edullisiaLounaitaMyyty(), 0);
    }
    
    @Test
    public void korttimaksuPalauttaaTrueEdullisilla() {
        Maksukortti m = new Maksukortti(1000);
        
        assertEquals(paate.syoEdullisesti(m), true);
    }
    
    @Test
    public void korttimaksuPalauttaaTrueMaukkailla() {
        Maksukortti m = new Maksukortti(1000);
        
        assertEquals(paate.syoMaukkaasti(m), true);
    }
    
    @Test
    public void kortiltaVeloitetaanOikeinEdullisilla() {
        Maksukortti m = new Maksukortti(1000);
        paate.syoEdullisesti(m);
        assertEquals(m.saldo(), 760);
    }
   
    @Test
    public void kortiltaVeloitetaanOikeinMaukkailla() {
        Maksukortti m = new Maksukortti(1000);
        paate.syoMaukkaasti(m);
        assertEquals(m.saldo(), 600);
    }
    
    @Test
    public void lounaidenMaaraKasvaaKortillaEdullisissa() {
        Maksukortti m = new Maksukortti(1000);
        paate.syoEdullisesti(m);
        assertEquals(paate.edullisiaLounaitaMyyty(), 1);
    }
    
    @Test
    public void lounaidenMaaraKasvaaKortillaMaukkaissa() {
        Maksukortti m = new Maksukortti(1000);
        paate.syoMaukkaasti(m);
        assertEquals(paate.maukkaitaLounaitaMyyty(), 1);
    }
    
    @Test
    public void riittamatonKorttimaksuEiKasvataEdullisissa() {
        Maksukortti m = new Maksukortti(10);
        paate.syoEdullisesti(m);
        assertEquals(paate.edullisiaLounaitaMyyty(), 0);
    }
    
    @Test
    public void riittamatonKorttimaksuEiKasvataMaukkaissa() {
        Maksukortti m = new Maksukortti(10);
        paate.syoMaukkaasti(m);
        assertEquals(paate.maukkaitaLounaitaMyyty(), 0);
    }
    
    @Test
    public void riittamatonPalauttaaFalseEdullisissa() {
        Maksukortti m = new Maksukortti(10);
        assertEquals(paate.syoEdullisesti(m), false);
    }
    
    @Test
    public void riittamatonPalauttaaFalseMaukkaiissa() {
        Maksukortti m = new Maksukortti(10);
        assertEquals(paate.syoMaukkaasti(m), false);
    }
    
    @Test
    public void kassanRahatEiMuutuKortillaOstettaessa() {
        Maksukortti m = new Maksukortti(10000);
        paate.syoEdullisesti(m);
        paate.syoMaukkaasti(m);
        assertEquals(paate.kassassaRahaa(), 100000);
    }
    
    @Test
    public void kortilleRahaaLadattaessaSaldoMuuttuu() {
        Maksukortti m = new Maksukortti(1000);
        paate.lataaRahaaKortille(m, 100);
        assertEquals(m.saldo(), 1100);
    }
    
    @Test
    public void kortilleRahaaLadattaessaKassaMuuttuu() {
        Maksukortti m = new Maksukortti(1000);
        paate.lataaRahaaKortille(m, 100);
        assertEquals(paate.kassassaRahaa(), 100100);
    }
    
    @Test
    public void kortilleRahaaLadattaessaNegatiivinenEiMuuta() {
        Maksukortti m = new Maksukortti(1000);
        paate.lataaRahaaKortille(m, -1);
        assertEquals(paate.kassassaRahaa(), 100000);
    }
}
