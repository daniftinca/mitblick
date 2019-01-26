package profile.boundary;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import exception.BusinessException;
import profile.service.PdfExportService;
import profile.service.ProfileManagementService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Path("/pdf")
public class PdfGeneratorBoundary {

    @EJB
    private PdfExportService pdfExportService;

    @EJB
    private ProfileManagementService profileManagementService;

    /**
     * Export a bug identified by an id into a pdf file
     *
     * @return Response with a pdf
     */
    @GET
    @Path("/profiles")
    // @Secured(PROFILE_EXPORT_PDF) ?
    @Produces("application/pdf")
    public Response getFile() {
        File file;
        FileOutputStream fileOutputStream;
        String localDir = System.getProperty("user.dir") + "\\ProfilesPdf.pdf";
        try {

            file = new File(localDir);
            Document document = new Document();
            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);
            pdfExportService.createPdf(profileManagementService.getAll(), document);

            Response.ResponseBuilder response = Response.ok(file);
            response.header("Content-Disposition", "attachment; filename=new-android-book.pdf");
            file.deleteOnExit();
            return response.build();

        } catch (DocumentException | BusinessException | IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}
