package br.com.locadora.main;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import br.com.locadora.view.ReservaApp;

public class Main {

	public static void main( String[] args )
    {   // estilo da interface 
        try {
            UIManager.setLookAndFeel( new NimbusLookAndFeel() );
         }
         catch ( Throwable ex ) {
            
         }
         
        ReservaApp app = new ReservaApp();
        app.setVisible(true);
    }
}
