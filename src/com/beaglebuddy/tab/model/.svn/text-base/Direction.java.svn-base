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
* This class represents a beaglebuddy tab direction and provides methods to read and write it to a beaglebuddy tab (.bbt) file.
* <p>
* <table>
*    <tr><th>Direction         </th><th>Symbol       </th><th>Description                                                                                   </th></tr>
*    <tr><td>Coda              </td><td>O+           </td><td>meaning "tail", it marks the beginning of a separate section                                  </td></tr>
*    <tr><td>Fine              </td><td>Fine         </td><td>meaning "end", it marks where the song ends                                                   </td></tr>
*    <tr><td>Segno             </td><td>S/           </td><td>meaning "sign", it marks a place where the song will return to                                </td></tr>
*    <tr><td>Da Capo           </td><td>D.C.         </td><td>return to the beginning                                                                       </td></tr>
*    <tr><td>Dal Segno         </td><td>D.S          </td><td>return to the segno symbol                                                                    </td></tr>
*    <tr><td>To Coda           </td><td>To Coda      </td><td>jump to the section marked with the Coda symbol O+                                            </td></tr>
*    <tr><td>Da Capo al Coda   </td><td>D.C. al Coda </td><td>return to the beginning, play until the "To Coda" is reached, then skip to the Coda symbol O+ </td></tr>
*    <tr><td>Dal Segno al Coda </td><td>D.S. al Coda </td><td>return to the segno, play until the "to Coda" is reached, then skip to the Coda symbol O+     </td></tr>
*    <tr><td>Da Capo al Fine   </td><td>D.C. al Fine </td><td>return to the beginning and play until the Fine                                               </td></tr>
*    <tr><td>Dal Segno al Fine </td><td>D.S. al Fine </td><td>return to the segno and play until the Fine                                                   </td></tr>
* <table>
* </p>
*/
public class Direction
{
   // enums
   public enum Symbol
   {
      None                     ("None")               , Coda                     ("Coda")               , DoubleCoda               ("Double Coda")        , Segno                    ("Segno")              ,
      SegnoSegno               ("Segno Segno")        , Fine                     ("Fine")               , DaCapo                   ("D.C.")               , DalSegno                 ("D.S.")               ,
      DalSegnoSegno            ("D.S.S.")             , ToCoda                   ("To Coda")            , ToDoubleCoda             ("To Dbl. Coda")       , DaCapoAlCoda             ("D.C. al Coda")       ,
      DaCapoAlDoubleCoda       ("D.C. al Dbl. Coda")  , DalSegnoAlCoda           ("D.S. al Coda")       , DalSegnoAlDoubleCoda     ("D.S. al Dbl. Coda")  , DalSegnoSegnoAlCoda      ("D.S.S. al Coda")     ,
      DalSegnoSegnoAlDoubleCoda("D.S.S. al Dbl. Coda"), DaCapoAlFine             ("D.C. al Fine")       , DalSegnoAlFine           ("D.S. al Fine")       , DalSegnoSegnoAlFine      ("D.S.S. al Fine");

      Symbol (String text)      {this.text = text;}
      private String text;
      public  String text()     {return text;}
      @Override
      public  String toString() {return text;}
   }

   // data members
   private Symbol symbol;          // the direction symbol
   private Symbol activatedBy;     // which symbol, if any, activates the direction
   private byte   numRepeats;      // number of times the direction symbol is encountered before it becomes active




   /**
    * default constructor.
    */
   public Direction()
   {
      symbol      = Symbol.None;
      activatedBy = Symbol.None;
      numRepeats  = 0;
   }

   /**
    * constructor.
    * <br/><br/>
    * @param symbol        the music direction symbol.
    * @param activatedBy   the music direction symbol, if any, which activate the music direction symbol.
    * @param numRepeats    the number of times the music direction symbol must be encountered before it becomes activated.
    */
   public Direction(Symbol symbol, Symbol activatedBy, byte numRepeats)
   {
      this.symbol     = symbol;
      this.numRepeats = numRepeats;

      setActivatedBy(activatedBy);
   }

   /**
    * copy constructor.
    * <br/><br/>
    * @param direction   the direction whose values will be deep copied.
    */
   public Direction(Direction direction)
   {
      this();

      if (direction != null)
      {
         this.symbol      = direction.symbol;
         this.activatedBy = direction.activatedBy;
         this.numRepeats  = direction.numRepeats;
      }
   }

   /*
    * @returns the music direction symbol.
    */
   public Symbol getSymbol()
   {
      return symbol;
   }

   /*
    * sets the music direction symbol.
    * <br/><br/>
    * @param symbol   the music direction symbol.
    */
   public void setSymbol(Symbol symbol)
   {
      this.symbol = symbol;
   }

   /*
    * @returns which symbol is used to activate a previous symbol.  for example, "DaCapo" can be used to activate a previous Fine or Coda symbol.
    */
   public Symbol getActivatedBy()
   {
      return activatedBy;
   }

   /*
    * sets the activation music direction symbol.
    * <br/><br/>
    * @param symbol   the music direction symbol that will activate a previously encountered music direction symbol.
    */
   public void setActivatedBy(Symbol activatedBy)
   {
      if (activatedBy != Symbol.None && activatedBy != Symbol.DaCapo && activatedBy != Symbol.DalSegno && activatedBy != Symbol.DalSegnoSegno)
         throw new IllegalArgumentException(ResourceBundle.format("error.invalid.activation_symbol", activatedBy, Symbol.None, Symbol.DaCapo, Symbol.DalSegno, Symbol.DalSegnoSegno));

      this.activatedBy = activatedBy;
   }

   /**
    * when a music direction symbol is encountered, it is ignored unless:
    * <ol>
    *    <li>its activation symbol is none.</li>
    *    <li>the specified activation symbol has been encountered.</li>
    *    <li>the number of specified repeats has occurred.</li>
    * </ol>
    * <br/><br/>
    * @return the number of repeats that must occur before the music direction symbol will be activated.
    */
   public byte getNumRepeats()
   {
      return numRepeats;
   }

   /*
    * sets the number of repeats that must occur before the music direction symbol is activated.
    * <br/><br/>
    * @param numRepeats   the number of repeats that must occur before the music direction symbol will be activated.
    */
   public void setNumRepeats(byte numRepeats)
   {
      this.numRepeats = numRepeats;
   }

   /**
    * @param symbol  the integer symbol.
    * <br/><br/>
    * @return the symbol type enum corresponding to the integer symbol type.
    */
   public static Symbol getSymbol(int symbol)
   {
      for (Symbol s : Symbol.values())
         if (symbol == s.ordinal())
            return s;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.symbol"), symbol, Symbol.DalSegnoSegnoAlFine.ordinal()));
   }

   /**
    * @param activatedBy  the integer activation symbol.
    * <br/><br/>
    * @return the active symbol enum corresponding to the integer activation symbol.
    */
   public static Symbol getActivatedBy(int activatedBy)
   {
      for (Symbol s : Symbol.values())
         if (activatedBy == s.ordinal())
            return s;
      throw new IllegalArgumentException(ResourceBundle.format("error.invalid.type", ResourceBundle.getString("data_type.symbol.active"), activatedBy, Symbol.DalSegnoSegno.ordinal()));
   }

   /**
    * @return whether two directions are equal.
    * <br/><br/>
    * @param object   object to check for equality.
    */
   @Override
   public boolean equals(Object object)
   {
      boolean equal = false;

      if (object != null && object instanceof Direction)
      {
         Direction direction = (Direction)object;
         equal = this.symbol == direction.symbol && this.activatedBy == direction.activatedBy && this.numRepeats == direction.numRepeats;
      }
      return equal;
   }

   /**
    * read in a direction from a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to read.
    * <br/><br/>
    * @throws FileReadException  if an error occurs while trying to read in the direction from the beaglebuddy tab file.
    */
   public void load(FileInputStream file) throws FileReadException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         symbol      = getSymbol     (file.read());
         activatedBy = getActivatedBy(file.read());
         numRepeats  = (byte)         file.read();
      }
      catch (Exception ex)
      {
         throw new FileReadException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.direction"));
      }
   }

   /**
    * save a direction to a beaglebuddy tab (.bbt) file.
    * <br/><br/>
    * @param file   the beaglebuddy tab file to write to.
    * <br/><br/>
    * @throws FileWriteException  if an error occurs while trying to write the direction to the beaglebuddy tab file.
    */
   public void save(FileOutputStream file) throws FileWriteException
   {
      long pos = -1L;
      try
      {
         pos = file.getPosition();
         file.write(symbol     .ordinal());
         file.write(activatedBy.ordinal());
         file.write(numRepeats           );
      }
      catch (Exception ex)
      {
         throw new FileWriteException(ex, file.getFilename(), pos, file.getSection(), file.getMeasure(), ResourceBundle.getString("data_type.direction"));
      }
   }

   /**
    * @return a string representation of the beaglebuddy tab direction.
    */
   @Override
   public String toString()
   {
      return toString(18);
   }

   /**
    * @param numSpacesToIndent  the number of spaces to indent when printing out the data members.
    * <br/><br/>
    * @return a string representation of the beaglebuddy tab direction.
    */
   public String toString(int numSpacesToIndent)
   {
      StringBuffer buffer = new StringBuffer();

      buffer.append(ResourceBundle.getString("text.symbol"             ) + ": " + symbol      + ", " +
                    ResourceBundle.getString("text.symbol.activated_by") + ": " + activatedBy + ", " +
                    ResourceBundle.getString("text.num_repeats"        ) + ": " + numRepeats);

      return buffer.toString();
   }
}
