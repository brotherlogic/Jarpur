package uk.co.brotherlogic.jarpur.replacers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import uk.co.brotherlogic.jarpur.LinkTable;

public class SimpleReplacer extends Replacer
{

   @Override
   public String process(Object ref, Map<String, Object> objectMap)
   {

      setRefObj(ref);

      if (replacement.startsWith("link"))
      {
         return LinkTable.getLinkTable().resolveLink(resolve(replacement.substring(5), objectMap));
      }

      Object obj = resolve(replacement, objectMap);

      if (obj instanceof Calendar)
      {
         DateFormat df = new SimpleDateFormat("dd/MM/yy");
         return df.format(((Calendar) obj).getTime());
      }

      return obj.toString();
   }

   @Override
   protected String getName()
   {
      return "Simple: " + replacement;
   }

   private final String replacement;

   public SimpleReplacer(String replacerText)
   {
      replacement = replacerText;
   }

   @Override
   public String toString()
   {
      return "Simple: " + replacement;
   }
}
