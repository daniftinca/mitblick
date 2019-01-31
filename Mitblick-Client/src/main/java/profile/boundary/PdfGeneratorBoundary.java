package profile.boundary;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import exception.BusinessException;
import profile.dto.ProfileDTO;
import profile.service.PdfExportService;
import profile.service.ProfileManagementService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Path("/pdf")
public class PdfGeneratorBoundary {

    public static final String PROFILES_PDF_LOCATION = "\\ProfilesPdf.pdf";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String USER_DIR = "user.dir";
    @EJB
    private PdfExportService pdfExportService;

    @EJB
    private ProfileManagementService profileManagementService;

    /**
     * Export all profiles into a pdf file.
     *
     * @return Response with a pdf | INTERNAL_SERVER_ERROR
     */
    @GET
    @Path("/profiles")
    // @Secured(PROFILE_EXPORT_PDF) ?
    @Produces("application/pdf")
    public Response getFile() {
        File file;
        FileOutputStream fileOutputStream;
        String localDir = System.getProperty(USER_DIR) + PROFILES_PDF_LOCATION;
        try {

            file = new File(localDir);
            Document document = new Document();
            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);
            pdfExportService.createPdf(profileManagementService.getAll(), document);

            Response.ResponseBuilder response = Response.ok(file);
            response.header(CONTENT_DISPOSITION, "attachment; filename=allProfiles.pdf");
            file.deleteOnExit();
            return response.entity(file).build();

        } catch (DocumentException | BusinessException | IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Export a profile, identified by an id, into a pdf file.
     *
     * @param email Profile email.
     * @return Response with a pdf | INTERNAL_SERVER_ERROR
     */
    @GET
    @Path("/profile-by-email/{email}")
    // @Secured(PROFILE_EXPORT_PDF) ?
    @Produces("application/pdf")
    public Response getFileById(@PathParam("email") String email) {
        File file;
        FileOutputStream fileOutputStream;
        String localDir = System.getProperty(USER_DIR) + PROFILES_PDF_LOCATION;
        try {

            file = new File(localDir);
            Document document = new Document();
            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);
            pdfExportService.createSinglePdf(profileManagementService.getByEmail(email), document);

            Response.ResponseBuilder response = Response.ok(file);
            response.header(CONTENT_DISPOSITION, "attachment; filename=profile_" + email.split("@")[0] + ".pdf");
            file.deleteOnExit();
            return response.entity(file).build();

        } catch (DocumentException | BusinessException | IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Export a list of profiles, identified by a list of emailss, into a pdf file.
     *
     * @param emailList List of profile emails.
     * @return Response with a pdf | INTERNAL_SERVER_ERROR
     */
    @GET
    @Path("/profiles-by-emails")
    // @Secured(PROFILE_EXPORT_PDF) ?
    @Produces("application/pdf")
    public Response getFileByEmailList(@QueryParam("emailList") List<String> emailList) {
        File file;
        FileOutputStream fileOutputStream;
        String localDir = System.getProperty(USER_DIR) + PROFILES_PDF_LOCATION;
        try {

            file = new File(localDir);
            Document document = new Document();
            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);

            List<ProfileDTO> profileDTOList = new ArrayList<>();
            for (String email : emailList) {
                profileDTOList.add(profileManagementService.getByEmail(email));
            }

            pdfExportService.createPdf(profileDTOList, document);

            Response.ResponseBuilder response = Response.ok(file);
            response.header(CONTENT_DISPOSITION, "attachment; filename=profileList.pdf");
            file.deleteOnExit();
            return response.entity(file).build();

        } catch (DocumentException | BusinessException | IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}
