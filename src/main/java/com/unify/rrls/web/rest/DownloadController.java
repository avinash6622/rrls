package com.unify.rrls.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

@Controller
public class DownloadController {

    @Autowired
    ServletContext context;
    @PersistenceContext
    EntityManager em;

    @RequestMapping(value = "/download/{fileID:.+}",method = RequestMethod.GET)
    public void downloader(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable("fileID")  int fileID) {
        try {

            Query q = em.createNativeQuery("select file_name, file_data from ra_file_upload where id = "+fileID+"");

           // String path = (String) q.getSingleResult();
            Object[] fileupload = (Object[]) q.getSingleResult();

            String path= (String) fileupload[1];

            String name= (String) fileupload[0];

          //  System.out.println("Filename---->"+path);

            int index = path.lastIndexOf("\\");
            String fileName = path.substring(index + 1);

            String[] array = fileName.split(".");
            array = fileName.split("\\.");

            array = fileName.split("[.]");

            String extension = array[array.length-1];


            String pathName= path.substring(0,path.lastIndexOf(File.separator));

           //String downloadFolder = context.getRealPath(pathName);

           File file = new File(pathName + File.separator + fileName);

           if (file.exists()) {
                String mimeType = context.getMimeType(file.getPath());

                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }

                response.setContentType(mimeType);
                response.addHeader("Content-Disposition", "attachment; filename=" + name+"."+extension);
                response.setContentLength((int) file.length());

                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int b = -1;

                while ((b = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, b);
                }

                fis.close();
                os.close();
            } else {
                System.out.println("Requested " + fileName + " file not found!!");
            }
        } catch (IOException e) {
            System.out.println("Error:- " + e.getMessage());
        }
    }
    
    @RequestMapping(value = "/download-external/{fileID:.+}",method = RequestMethod.GET)
    public void downloadExternal(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable("fileID")  int fileID) {
        try {

            Query q = em.createNativeQuery("select file_name, file_data from external_file_upload where id = "+fileID+"");

           // String path = (String) q.getSingleResult();
            Object[] fileupload = (Object[]) q.getSingleResult();

            String path= (String) fileupload[1];

            String name= (String) fileupload[0];

          //  System.out.println("Filename---->"+path);

            int index = path.lastIndexOf("\\");
            String fileName = path.substring(index + 1);

            String[] array = fileName.split(".");
            array = fileName.split("\\.");

            array = fileName.split("[.]");

            String extension = array[array.length-1];


            String pathName= path.substring(0,path.lastIndexOf(File.separator));

           //String downloadFolder = context.getRealPath(pathName);

           File file = new File(pathName + File.separator + fileName);

           if (file.exists()) {
                String mimeType = context.getMimeType(file.getPath());

                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }

                response.setContentType(mimeType);
                response.addHeader("Content-Disposition", "attachment; filename=" + name+"."+extension);
                response.setContentLength((int) file.length());

                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int b = -1;

                while ((b = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, b);
                }

                fis.close();
                os.close();
            } else {
                System.out.println("Requested " + fileName + " file not found!!");
            }
        } catch (IOException e) {
            System.out.println("Error:- " + e.getMessage());
        }
    }
}
