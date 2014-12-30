/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.model;

import com.beaglebuddy.tab.io.FileInputStream;
import com.beaglebuddy.tab.io.FileOutputStream;
import com.beaglebuddy.tab.io.FileReadException;
import com.beaglebuddy.tab.io.FileWriteException;
import com.beaglebuddy.tab.resource_bundle.ResourceBundle;





/**
 * This class represents a beaglebuddy tab key signature and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
 * <p>
 * note:
 * An interesting case occurs when a key signature other than C Major\A Minor is selected, and then, at some point later on in the score,
 * a key change to C Major\A Minor is desired.  Since C Major\A Minor don't have any accidentals, in order to indicate the key change, naturals
 * must be used to nullify the previous key's accidentals.  This is indicated by the "cancellation" data member.
 * </p>
 */
public class KeySignature
{
   // number and type of accidentals in a key
   public enum Accidentals
   {
      None       ,
      OneSharp   , TwoSharps  , ThreeSharps, FourSharps , FiveSharps , SixSharps  , SevenSharps,
      OneFlat    , TwoFlats   , ThreeFlats , FourFlats  , FiveFlats  , SixFlats   , SevenFlats
   }

   // key types
   public enum Type {Major, Minor}

   // data members
   private Accidentals accidentals;   // number and type of accidentals in a key
   private Type        type;          // key type
   private boolean     show;          // key signature is displayed
   private boolean     cancellation;  // key signature is a cancellation





   /**
    * default constructor.
    */
   public KeySignature()
   {
      this.accidentals = Accidentals.None;
      this.type        = Type.Major;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param accidentals   the number and type of accidentals in the key.
    * @param type          the key type.
    */
   public KeySignature(Accidentals accidentals, Type type)
   {
      this.accidentals  = accidentals;
      this.type         = type;
      this.show         = false;
      this.cancellation = false;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param accidentals   the number and type of accidentals in the key.
    * @param type          the key type.
    * @param show          whether the key signature is displayed.
    * @param cancellation  whether the key signature is a cancellation.
    */
   public KeySignature(Accidentals accidentals, Type type, boolean show, boolean cancellation)
   {
      this.accidentals  = accidentals;
      this.type         = type;
      this.show         = show;
      this.cancellation = cancellation;
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param keySignature   the key signature whose values will be deep copied.
    */
   public KeySignature(KeySignature keySignature)
   {
      this();

      if (keySignature != null)
      {
         this.accidentals  = keySignature.accidentals;
         this.type         = keySignature.type;
         this.show         = keySignature.show;
         this.cancellation = keySignature.cancellation;
      }
   }

   /**
    * @return the number and type of accidentals in the key.
    */
   public Accidentals getAccidentals()
   {
      return accidentals;
   }

   /**
    * @param accidentals  the number and type of accidentals in the key.
    * <br/><br/>
    * @return the accidentals enum corresponding to the integer accidentals type.
    */
   public static Accidentals getAccidentals(int accidentals)
   {
      for (Accidentals a : Accidentals.values())
         if (accidentals == a.ordinal())
            return a;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.key_accidentals"), accidentals, Accidentals.SevenFlats.ordinal()));
   }

   /**
    * @return whether the key has any accidentals.
    */
   public boolean hasAccidentals()
   {
      return accidentals != Accidentals.None;
   }

   /**
    * sets the number and type of accidentals in the key.
    * <br/><br/>
    * @param accidentals  the number and type of accidentals in the key.
    */
   public void setAccidentals(Accidentals accidentals)
   {
      this.accidentals = accidentals;
   }

   /**
    * @return the number of accidentals in the key.
    */
   public int getNumberOfAccidentals()
   {
      return getNumberOfFlats() == 0 ? getNumberOfSharps() : getNumberOfFlats();
   }

   /**
    * @return the number of flats in the key.
    */
   public int getNumberOfFlats()
   {
      int numFlats = 0;

      switch (accidentals)
      {
         case OneFlat:     numFlats = 1;  break;
         case TwoFlats:    numFlats = 2;  break;
         case ThreeFlats:  numFlats = 3;  break;
         case FourFlats:   numFlats = 4;  break;
         case FiveFlats:   numFlats = 5;  break;
         case SixFlats:    numFlats = 6;  break;
         case SevenFlats:  numFlats = 7;  break;
      }
      return numFlats;
   }

   /**
    * @return the number of sharps in the key.
    */
   public int getNumberOfSharps()
   {
      int numSharps = 0;

      switch (accidentals)
      {
         case OneSharp:     numSharps = 1;  break;
         case TwoSharps:    numSharps = 2;  break;
         case ThreeSharps:  numSharps = 3;  break;
         case FourSharps:   numSharps = 4;  break;
         case FiveSharps:   numSharps = 5;  break;
         case SixSharps:    numSharps = 6;  break;
         case SevenSharps:  numSharps = 7;  break;
      }
      return numSharps;
   }

   /**
    * @return whether the key uses flats.
    */
   public boolean usesFlats()
   {
      return getNumberOfFlats() != 0;
   }

   /**
    * @return whether the key uses sharps.
    */
   public boolean usesSharps()
   {
      return getNumberOfSharps() != 0;
   }

   /**
    * @return whether the key is a major key.
    */
   public boolean isMajor()
   {
      return type == Type.Major;
   }

   /**
    * @return whether the key is a minor key.
    */
   public boolean isMinor()
   {
      return type == Type.Minor;
   }

   /**
    * @param type   the type of type key.
    * <br/><br/>
    * @return the type enum corresponding to the integer type.
    */
   public static Type getType(int type)
   {
      for (Type t : Type.values())
         if (type == t.ordinal())
            return t;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.key_type"), type, Type.Minor.ordinal()));
   }

   /**
    * sets the type of key.
    * <br/><br/>
    * @param type  the type of key.
    */
   public void setType(Type type)
   {
      this.type = type;
   }

   /**
    * @return whether the key signature is displayed.
    */
   public boolean isShown()
   {
      return show;
   }

   /**
    * sets whether the key signature is displayed or not.
    * <br/><br/>
    * @param show   whether the key signature is displayed or not.
    */
   public void setShown(boolean show)
   {
      this.show = show;
   }

   /**
    * @return whether the key signature is a cancellation.
    */
   public boolean isCancellation()
   {
      return cancellation;
   }

   /**
    * sets whether the key signature is a cancellation.
    * <br/><br/>
    * @param cancel   whether the key signature is a cancellation.
    */
   public void setCancellation(boolean cancel)
   {
      this.cancellation = cancel;
   }

   /**
    * @return the name of the key.
    */
   public String getName()
   {
      String[] majorSharpNames = {"C" , "G" , "D" , "A" , "E" , "B" , "F#", "C#"};
      String[] minorSharpNames = {"A" , "E" , "B" , "F#", "C#", "G#", "D#", "A#"};
      String[] majorFlatNames  = {"C" , "F" , "Bb", "Eb", "Ab", "Db", "Gb", "Cb"};
      String[] minorFlatNames  = {"A" , "D" , "G" , "C" , "F" , "Bb", "Eb", "Ab"};

      int numSharps = getNumberOfSharps();
      int numFlats  = getNumberOfFlats ();

      String name = null;

      if (numSharps == 0)
         name = (type == Type.Major ? majorFlatNames [numFlats ] : minorFlatNames [numFlats ]);
      else
         name = (type == Type.Major ? majorSharpNames[numSharps] : minorSharpNames[numSharps]);
      name += " " + ResourceBundle.getString(type == Type.Major ? "text.major" : "text.minor");

      return name;
   }

   /**
    * @return whether two key signatures equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
  public boolean equals(Object object)
   {
      boolean equal = false;
      if (object != null && object instanceof KeySignature)
      {
         KeySignature keySignature = (KeySignature)object;
         equal = this.accidentals  == keySignature.accidentals &&
                 this.type         == keySignature.type        &&
                 this.show         == keySignature.show        &&
                 this.cancellation == keySignature.cancellation;
      }
      return equal;
   }

   /**
    * read in a key signature from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException   if an error occurs while trying to read in the key signature from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos          = file.getPosition();
         accidentals  = getAccidentals(file.read());
         type         = file.read() == 0 ? Type.Major : Type.Minor;
         show         = file.readBoolean();
         cancellation = file.readBoolean();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.key_signature"));
      }
   }

   /**
    * save a key signature to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the key signature to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write       (accidentals.ordinal());
         file.write       (type.ordinal());
         file.writeBoolean(show);
         file.writeBoolean(cancellation);
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.key_signature"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab key signature.
    */
   @Override
   public String toString()
   {
      return toString(18);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab key signature.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer      = new StringBuffer();
      String       indentation = Utility.indent(numSpacesToIndent);

      buffer.append(getName() + " \n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.type"           ), indentation) + type         + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.accidentals"    ), indentation) + accidentals  + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.is_shown"       ), indentation) + show         + "\n");
      buffer.append(Utility.pad(ResourceBundle.getString("text.is_cancellation"), indentation) + cancellation       );

      return buffer.toString();
   }

   /**
    * @return the type of accidental used in the key signature.
    */
   public Accidental toAccidental()
   {
      Accidental accidental;
      if (isCancellation())
      {
         accidental = Accidental.Natural;
      }
      else if (usesFlats())
      {
         accidental = Accidental.Flat;
      }
      else if (usesSharps())
      {
         accidental = Accidental.Sharp;
      }
      else
      {
         throw new RuntimeException("key signature illegal state");
      }
      return accidental;
   }
}
