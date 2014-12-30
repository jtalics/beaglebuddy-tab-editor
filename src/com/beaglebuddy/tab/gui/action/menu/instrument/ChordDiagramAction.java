/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.action.menu.instrument;

import com.beaglebuddy.tab.gui.action.BaseAction;
import com.beaglebuddy.tab.gui.dialog.chord.diagram.ChordDiagramsDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.ChordDiagram;
import com.beaglebuddy.tab.model.instrument.Instrument;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;




/**
 * called when the user selects the <i>Chord Diagram</i> menu item from the <i>Instrument</i> menu.
 */
public class ChordDiagramAction extends BaseAction
{
   /**
    * constructor
    * <br/><br/>
    * @param frame   main tab application frame.
    */
   public ChordDiagramAction(MainFrame frame)
   {
      super(frame);
   }

   /**
    * shows a dialog box that lets users create chord diagrams.
    * <br/><br/>
    * @param event  action event which caused this method to be invoked.
    */
   @Override
   public void actionPerformed(ActionEvent event)
   {
      // get the current list of chord diagrams
      ArrayList<ChordDiagram> chordDiagrams = frame.getSong().getChordDiagrams();

      // get a list of all instruments which can be associated with a chord diagram
      ArrayList<Instrument>   instruments             = frame.getSong().getScore().getInstruments();
      ArrayList<Instrument>   chordDiagramInstruments = new ArrayList<Instrument>();

      int i=0;
      for(Instrument instrument : instruments)
         if (instrument.canHaveAssociatedChordDiagrams())
            chordDiagramInstruments.add(instrument);

      // if there aren't any instruments defined which can have chord diagrams, then display an error message
      if (chordDiagramInstruments.size() == 0)
      {
         JOptionPane.showMessageDialog(null, ResourceBundle.format("gui.error.chord.diagram.no_valid_instruments_defined",
                                       ResourceBundle.getString("instrument.vocals"), ResourceBundle.getString("instrument.guitar"), ResourceBundle.getString("instrument.keyboard"), ResourceBundle.getString("instrument.other.treble")),
                                       ResourceBundle.getString("gui.text.dialog_title.error"), JOptionPane.ERROR_MESSAGE);
      }
      else
      {
         // show the dialog and let the user add, edit, and delete the current set of chord diagrams.
         ChordDiagramsDialog dialog = new ChordDiagramsDialog(frame, chordDiagramInstruments, chordDiagrams);
         dialog.setVisible(true);

         // if the user pressed ok, then save the user's changes to the song
         if (dialog.wasOKed())
         {
            // get the chord diagrams after the user has edited them
            ArrayList<ChordDiagram> newChordDiagrams = dialog.getChordDiagrams();

            // save them to the song
            frame.getSong().setChordDiagrams(newChordDiagrams);
         }
      }
   }
}
