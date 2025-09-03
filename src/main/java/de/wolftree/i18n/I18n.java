package de.wolftree.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
   private final Locale locale;
   private final ResourceBundle bundle;

   public I18n(Locale locale) {
      this.locale = locale;
      this.bundle = ResourceBundle.getBundle("messages", locale);
   }

   public String tr(String key, Object... args) {
      String pattern = bundle.getString(key);
      return MessageFormat.format(pattern, args);
   }
}
