/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.dialog.tab_symbols;

import com.beaglebuddy.tab.gui.dialog.BaseDialog;
import com.beaglebuddy.tab.gui.mainframe.MainFrame;
import com.beaglebuddy.tab.model.attribute.chord.FingerFretHand;
import com.beaglebuddy.tab.model.attribute.chord.FingerPickHand;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;




/**
 * This class is a dialog box which allows a user to specify how a chord is vibrato'd.
 */
public class FingeringDialog extends BaseDialog
{
   // data members
   private FingerFretHand fretHandFinger;
   private FingerPickHand pickHandFinger;

   // controls
   JRadioButton radioButtonFretHandNone;
   JRadioButton radioButtonFretHandThumb;
   JRadioButton radioButtonFretHandOne;
   JRadioButton radioButtonFretHandTwo;
   JRadioButton radioButtonFretHandThree;
   JRadioButton radioButtonFretHandFour;
   ButtonGroup  buttonGroupFretHand;

   JRadioButton radioButtonPickHandNone;
   JRadioButton radioButtonPickHandThumb;
   JRadioButton radioButtonPickHandIndex;
   JRadioButton radioButtonPickHandMiddle;
   JRadioButton radioButtonPickHandRing;
   JRadioButton radioButtonPickHandPinky;
   ButtonGroup  buttonGroupPickHand;



   /**
    * constructor.
    * <br/><br/>
    * @param frame   the main application frame.
    * @param fretHandFinger  the fret hand fretHandFinger to use to play the note.
    */
   public FingeringDialog(MainFrame frame, FingerFretHand fretHandFinger, FingerPickHand pickHandFinger)
   {
      super(frame, frame.getHelpBroker(), frame.getHelpSet(), "dialog.fingering");

      // make a copy of the fret hand fretHandFinger the user will be editing
      this.fretHandFinger = (fretHandFinger == null ? null : (FingerFretHand)fretHandFinger.clone());
      this.pickHandFinger = (pickHandFinger == null ? null : (FingerPickHand)pickHandFinger.clone());

      // set the dialog box title
      setTitle(ResourceBundle.getString("dialog.fingering.title"));
   }

   /**
    * this method implements the abstract base class method, and is used to add the swing gui controls to the top half of the dialog box.
    */
   @Override
   public void addComponents()
   {
      JPanel panelFretHand = createFretHandRadioButtons();
      JPanel panelPickHand = createPickHandRadioButtons();

      // set the layout manager
      JPanel       controlsPanel = getControlsPanel();
      GroupLayout  layout        = new GroupLayout(controlsPanel);
      controlsPanel.setLayout(layout);

      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(panelFretHand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(panelPickHand, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addComponent(panelPickHand, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         .addComponent(panelFretHand, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );

      // invoke the layout manager
      pack();
   }

   /**
    * create the fret hand radio buttons.
    */
   private JPanel createFretHandRadioButtons()
   {
      radioButtonFretHandNone  = new JRadioButton(ResourceBundle.getString("dialog.fingering.fret_hand.label.none" ));
      radioButtonFretHandThumb = new JRadioButton(ResourceBundle.getString("dialog.fingering.fret_hand.label.thumb"));
      radioButtonFretHandOne   = new JRadioButton(ResourceBundle.getString("dialog.fingering.fret_hand.label.one"  ));
      radioButtonFretHandTwo   = new JRadioButton(ResourceBundle.getString("dialog.fingering.fret_hand.label.two"  ));
      radioButtonFretHandThree = new JRadioButton(ResourceBundle.getString("dialog.fingering.fret_hand.label.three"));
      radioButtonFretHandFour  = new JRadioButton(ResourceBundle.getString("dialog.fingering.fret_hand.label.four" ));

      radioButtonFretHandNone .setActionCommand("none" );
      radioButtonFretHandThumb.setActionCommand("thumb");
      radioButtonFretHandOne  .setActionCommand("one"  );
      radioButtonFretHandTwo  .setActionCommand("two"  );
      radioButtonFretHandThree.setActionCommand("three");
      radioButtonFretHandFour .setActionCommand("four" );

      radioButtonFretHandNone .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.none" ));
      radioButtonFretHandThumb.setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.thumb"));
      radioButtonFretHandOne  .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.one"  ));
      radioButtonFretHandTwo  .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.two"  ));
      radioButtonFretHandThree.setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.three"));
      radioButtonFretHandFour .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.four" ));

      // create the button group that groups the radio buttons together
      buttonGroupFretHand = new ButtonGroup();
      buttonGroupFretHand.add(radioButtonFretHandNone );
      buttonGroupFretHand.add(radioButtonFretHandThumb);
      buttonGroupFretHand.add(radioButtonFretHandOne  );
      buttonGroupFretHand.add(radioButtonFretHandTwo  );
      buttonGroupFretHand.add(radioButtonFretHandThree);
      buttonGroupFretHand.add(radioButtonFretHandFour );

      // set the initially selected radio button
      if (fretHandFinger == null)
      {
         radioButtonFretHandNone.setSelected(true);
      }
      else
      {
         switch (fretHandFinger.getFinger())
         {
            case Thumb:  radioButtonFretHandThumb.setSelected(true); break;
            case One:    radioButtonFretHandOne  .setSelected(true); break;
            case Two:    radioButtonFretHandTwo  .setSelected(true); break;
            case Three:  radioButtonFretHandThree.setSelected(true); break;
            case Four:   radioButtonFretHandFour .setSelected(true); break;
         }
      }

      // create the panel and add the radio buttons
      JPanel panelFretHand = new JPanel();
      panelFretHand.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.fingering.fret_hand.title")));

      GroupLayout layout = new GroupLayout(panelFretHand);
      panelFretHand.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(radioButtonFretHandNone )
               .addComponent(radioButtonFretHandThumb)
               .addComponent(radioButtonFretHandOne  )
               .addComponent(radioButtonFretHandTwo  )
               .addComponent(radioButtonFretHandThree)
               .addComponent(radioButtonFretHandFour ))
            .addContainerGap(60, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {radioButtonFretHandNone, radioButtonFretHandThumb, radioButtonFretHandOne, radioButtonFretHandTwo, radioButtonFretHandThree, radioButtonFretHandFour});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(radioButtonFretHandNone)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonFretHandThumb)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonFretHandOne  )
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonFretHandTwo  )
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonFretHandThree)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonFretHandFour ))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {radioButtonFretHandNone, radioButtonFretHandThumb, radioButtonFretHandOne, radioButtonFretHandTwo, radioButtonFretHandThree, radioButtonFretHandFour});

      return panelFretHand;
   }

   /**
    * create the pick hand radio buttons.
    */
   private JPanel createPickHandRadioButtons()
   {
      radioButtonPickHandNone   = new JRadioButton(ResourceBundle.getString("dialog.fingering.pick_hand.label.none"  ));
      radioButtonPickHandThumb  = new JRadioButton(ResourceBundle.getString("dialog.fingering.pick_hand.label.thumb" ));
      radioButtonPickHandIndex  = new JRadioButton(ResourceBundle.getString("dialog.fingering.pick_hand.label.index" ));
      radioButtonPickHandMiddle = new JRadioButton(ResourceBundle.getString("dialog.fingering.pick_hand.label.middle"));
      radioButtonPickHandRing   = new JRadioButton(ResourceBundle.getString("dialog.fingering.pick_hand.label.ring"  ));
      radioButtonPickHandPinky  = new JRadioButton(ResourceBundle.getString("dialog.fingering.pick_hand.label.pinky" ));

      radioButtonPickHandNone  .setActionCommand("none"  );
      radioButtonPickHandThumb .setActionCommand("thumb" );
      radioButtonPickHandIndex .setActionCommand("index" );
      radioButtonPickHandMiddle.setActionCommand("middle");
      radioButtonPickHandRing  .setActionCommand("ring"  );
      radioButtonPickHandPinky .setActionCommand("pinky" );

      radioButtonPickHandNone  .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.none"  ));
      radioButtonPickHandThumb .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.thumb" ));
      radioButtonPickHandIndex .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.index" ));
      radioButtonPickHandMiddle.setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.middle"));
      radioButtonPickHandRing  .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.ring"  ));
      radioButtonPickHandPinky .setToolTipText(ResourceBundle.getString("dialog.fingering.fret_hand.tooltip.pinky" ));

      // create the button group that groups the radio buttons together
      buttonGroupPickHand = new ButtonGroup();
      buttonGroupPickHand.add(radioButtonPickHandNone  );
      buttonGroupPickHand.add(radioButtonPickHandThumb );
      buttonGroupPickHand.add(radioButtonPickHandIndex );
      buttonGroupPickHand.add(radioButtonPickHandMiddle);
      buttonGroupPickHand.add(radioButtonPickHandRing  );
      buttonGroupPickHand.add(radioButtonPickHandPinky );

      // set the initially selected radio button
      if (pickHandFinger == null)
      {
         radioButtonPickHandNone.setSelected(true);
      }
      else
      {
         switch (pickHandFinger.getFinger())
         {
            case Thumb:  radioButtonPickHandThumb .setSelected(true); break;
            case Index:  radioButtonPickHandIndex .setSelected(true); break;
            case Middle: radioButtonPickHandMiddle.setSelected(true); break;
            case Ring:   radioButtonPickHandRing  .setSelected(true); break;
            case Pinky:  radioButtonPickHandPinky .setSelected(true); break;
         }
      }

      // create the panel and add the radio buttons
      JPanel panelPickHand = new JPanel();
      panelPickHand.setBorder(BorderFactory.createTitledBorder(ResourceBundle.getString("dialog.fingering.pick_hand.title")));

      GroupLayout layout = new GroupLayout(panelPickHand);
      panelPickHand.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
               .addComponent(radioButtonPickHandNone  )
               .addComponent(radioButtonPickHandThumb )
               .addComponent(radioButtonPickHandIndex )
               .addComponent(radioButtonPickHandMiddle)
               .addComponent(radioButtonPickHandRing  )
               .addComponent(radioButtonPickHandPinky ))
            .addContainerGap(60, Short.MAX_VALUE))
      );
      layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {radioButtonPickHandNone, radioButtonPickHandThumb, radioButtonPickHandIndex, radioButtonPickHandMiddle, radioButtonPickHandRing, radioButtonPickHandPinky});

      layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(radioButtonPickHandNone  )
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonPickHandThumb )
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonPickHandIndex )
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonPickHandMiddle)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonPickHandRing  )
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(radioButtonPickHandPinky ))
      );
      layout.linkSize(SwingConstants.VERTICAL, new Component[] {radioButtonPickHandNone, radioButtonPickHandThumb, radioButtonPickHandIndex, radioButtonPickHandMiddle, radioButtonPickHandRing, radioButtonPickHandPinky});

      return panelPickHand;
   }

   /**
    * @return the fret hand finger the user edited.
    */
   public FingerFretHand getFretHandFinger()
   {
      return fretHandFinger;
   }

   /**
    * @return the pick hand finger the user edited.
    */
   public FingerPickHand getPickHandFinger()
   {
      return pickHandFinger;
   }

   /**
    * this method implements the abstract base class method.
    * It is called when the user presses the <i>ok</i> button.
    */
   @Override
   public void ok()
   {
           if (radioButtonFretHandNone  .isSelected()) fretHandFinger = null;
      else if (radioButtonFretHandThumb .isSelected()) fretHandFinger = new FingerFretHand(FingerFretHand.Finger.Thumb);
      else if (radioButtonFretHandOne   .isSelected()) fretHandFinger = new FingerFretHand(FingerFretHand.Finger.One  );
      else if (radioButtonFretHandTwo   .isSelected()) fretHandFinger = new FingerFretHand(FingerFretHand.Finger.Two  );
      else if (radioButtonFretHandThree .isSelected()) fretHandFinger = new FingerFretHand(FingerFretHand.Finger.Three);
      else if (radioButtonFretHandFour  .isSelected()) fretHandFinger = new FingerFretHand(FingerFretHand.Finger.Four );

           if (radioButtonPickHandNone  .isSelected()) pickHandFinger = null;
      else if (radioButtonPickHandThumb .isSelected()) pickHandFinger = new FingerPickHand(FingerPickHand.Finger.Thumb );
      else if (radioButtonPickHandIndex .isSelected()) pickHandFinger = new FingerPickHand(FingerPickHand.Finger.Index );
      else if (radioButtonPickHandMiddle.isSelected()) pickHandFinger = new FingerPickHand(FingerPickHand.Finger.Middle);
      else if (radioButtonPickHandRing  .isSelected()) pickHandFinger = new FingerPickHand(FingerPickHand.Finger.Ring  );
      else if (radioButtonPickHandPinky .isSelected()) pickHandFinger = new FingerPickHand(FingerPickHand.Finger.Pinky );
   }
}
