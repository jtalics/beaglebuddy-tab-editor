package com.beaglebuddy.tab.gui.mainframe;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class SerializablePageFormat extends PageFormat implements Serializable
{
   private static final long serialVersionUID = 1L;

   public SerializablePageFormat()
   {
   }

   public SerializablePageFormat(PageFormat pf)
   {
      this.setOrientation(pf.getOrientation());
      this.setPaper(pf.getPaper());
   }

   private void writeObject(java.io.ObjectOutputStream out) throws IOException
   {
//      System.out.println("WRITING " + this);
      out.writeInt(this.getOrientation());
      out.writeDouble(this.getImageableX());
      out.writeDouble(this.getImageableY());
      out.writeDouble(this.getImageableWidth());
      out.writeDouble(this.getImageableHeight());
      out.writeDouble(this.getWidth());
      out.writeDouble(this.getHeight());
   }

   private void readObject(java.io.ObjectInputStream in)
      throws IOException, ClassNotFoundException
   {
      this.setOrientation(in.readInt());
      Paper p = getPaper();
      p.setImageableArea(in.readDouble(),
                         in.readDouble(),
                         in.readDouble(),
                         in.readDouble());
      p.setSize(in.readDouble(),
                in.readDouble());
      setPaper(p);

//      System.out.println("READ " + this);
   }

   @SuppressWarnings("unused")
   private void readObjectNoData() throws ObjectStreamException
   {
      throw new NotActiveException();
   }
   
   @Override
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getClass().getName());
      sb.append('@');
      sb.append(this.getHeight());
      sb.append(',');
      sb.append(this.getWidth());
      sb.append(',');
      sb.append(this.getImageableHeight());
      sb.append(',');
      sb.append(this.getImageableWidth());
      sb.append(',');
      sb.append(this.getImageableY());
      sb.append(',');
      sb.append(this.getImageableX());
      return sb.toString();
   }
}
