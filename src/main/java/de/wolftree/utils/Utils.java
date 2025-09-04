package de.wolftree.utils;

import de.wolftree.wolfchat.ChatService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

public class Utils {

   private final static int DEFAULT_LENGTH = 12;

   public static String readResourceFile(String path) throws IOException {
      //final Logger logger = LoggerFactory.getLogger(ProxyHandler.class);
      Path fileOnDisk = Paths.get(path);

      if (Files.exists(fileOnDisk)) {
         //logger.info("read from disk {}", fileOnDisk);
         return FileUtils.readFileToString(fileOnDisk.toFile(), "UTF-8");
      }

      InputStream inputStream = ChatService.class.getClassLoader().getResourceAsStream(path);
      if (inputStream != null) {
         //logger.info("read from jar");
         return IOUtils.toString(inputStream, "UTF-8");
      } else {
         throw new FileNotFoundException("file not found " + path);
      }
   }

   /**
    * List files from the given directory, if run from jar then jar if not then from disk
    *
    * @param relPath
    * @return
    * @throws IOException
    */
   public static List<String> listFiles(String relPath) throws IOException {
      List<String> fileNames = new ArrayList<>();

      String myPath = ChatService.class.getProtectionDomain().getCodeSource().getLocation()
          .getPath();
      if (myPath.endsWith(".jar")) {
         fileNames.addAll(listFilesFromJar(relPath));
      } else {

         try (
             InputStream in = Thread.currentThread().getContextClassLoader()
                 .getResourceAsStream(relPath);
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;
            while ((resource = br.readLine()) != null) {
               fileNames.add(resource);
            }
         }
      }

      return fileNames;
   }

   public static List<String> listFilesFromJar(String path) throws IOException {
      List<String> fileNames = new ArrayList<>();

      String jarPath = ChatService.class.getProtectionDomain().getCodeSource().getLocation()
          .getPath();
      if (jarPath.endsWith(".jar")) {

         try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
               JarEntry entry = entries.nextElement();
               if (entry.getName().startsWith(path + "/") && !entry.isDirectory()) {
                  fileNames.add(entry.getName().substring(path.length() + 1));
               }
            }
         }
      }

      return fileNames;
   }


   /**
    * Tries to load the given file from filesystem. if not found it tries to load it from ressources
    * in the jar
    *
    * @param filePath
    * @return String the contents of the file
    * @throws IOException
    */
   public static String loadFile(@NotNull String filePath) throws IOException {
      Path path = Path.of(filePath);

      if (Files.isReadable(path)) {
         return Files.readString(path);
      }

      try (InputStream resourceStream = Utils.class.getClassLoader()
          .getResourceAsStream(filePath)) {
         if (resourceStream != null) {
            return IOUtils.toString(resourceStream, StandardCharsets.UTF_8);
         }
      }

      throw new FileNotFoundException("File not found: " + filePath);
   }


}
