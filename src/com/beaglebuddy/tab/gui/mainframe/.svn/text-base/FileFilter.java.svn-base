/*******************************************************************************
 * $Workfile: $
 * $Logfile: $
 * $Revision: $
 * $Author: joneric Wennerstrom $
 *
 * © 2008 Beaglebuddy Software.   All rights reserved.
 *******************************************************************************/
package com.beaglebuddy.tab.gui.mainframe;

import com.beaglebuddy.tab.resource_bundle.ResourceBundle;
import java.io.File;




/**
 *
 */
public class FileFilter extends javax.swing.filechooser.FileFilter
{
   // data members
   private String[] allowableExtensions;    // list of extensions that will be displayed in the JFileChooser dialog.
   private String   description;            // description of the extensions




   /**
    * constructor.
    * <br/><br/>
    * @param extension    an extension that will be displayed in the JFileChooser dialog.
    * @param description  description of the extension.
    */
   public FileFilter(String extension, String description)
   {
      this(new String[] {extension}, description);
   }

   /**
    * constructor.
    * <br/><br/>
    * @param extensions   array of extensions that will be displayed in the JFileChooser dialog.
    * @param description  description of the extensions.
    */
   public FileFilter(String[] extensions, String description)
   {
      if (extensions == null || extensions.length == 0)
         throw new IllegalArgumentException(ResourceBundle.getString("gui.error.invalid.file_filter.extensions"));
      if (description == null || description.trim().length() == 0)
         throw new IllegalArgumentException(ResourceBundle.getString("gui.error.invalid.file_filter.description"));

      this.allowableExtensions = extensions;

      StringBuffer buffer = new StringBuffer();
      buffer.append(description);
      buffer.append(" (");

      for(String allowedExtension : allowableExtensions)
      {
         buffer.append("*.");
         buffer.append(allowedExtension);
         buffer.append("), ");
      }
      // remove the trailing ", ";
      this.description = buffer.substring(0, buffer.length() - 2);
   }

   /**
    * implements a method in the FileFilter interface which determines if a file should be displayed in the JFileChooser dialog.
    * <br/><br/>
    * @param file  file being considered for display in a JFileChooser dialog.
    * <br/><br/>
    * @return true if the file is a directory, or if the file has a .ptb or .bbt file extension.
    */
   @Override
   public boolean accept(File file)
   {
      return file != null && (file.isDirectory() || isAllowed(getExtension(file)));
   }

   /**
    * @return the file's extension if present.  null otherwise.
    * <br/><br/>
    * @param file  file being considered for display in a JFileChooser dialog.
    */
   private String getExtension(File file)
   {
      String extension = null;

      if (file != null)
      {
         String filename = file.getName();
         int    i        = filename.lastIndexOf('.');
         if (i > 0 && i < filename.length() - 1)
            extension = filename.substring(i+1);
      }
      return extension;
	}

   /**
    * @return if the file extension is one that should be displayed in the filters drop down list in the JFileChooser dialog.
    * <br/><br/>
    * @param extension the extension of a file being considered for display in a JFileChooser dialog.
    */
   private boolean isAllowed(String extension)
   {
      boolean allowed = false;
      if (extension != null) {
         for(int i=0; i < allowableExtensions.length && !allowed; ++i)
            allowed = extension.equals(allowableExtensions[i]);
      }
      return allowed;
   }

   /**
    * implements a method in the FileFilter interface.
    * <br/><br/>
    * @return the description of the allowable file types in the file chooser dialog.
    */
   @Override
   public String getDescription()
   {
      return description;
   }
}
