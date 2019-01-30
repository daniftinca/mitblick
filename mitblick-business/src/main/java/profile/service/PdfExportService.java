package profile.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.codec.Base64;
import exception.BusinessException;
import exception.ExceptionCode;
import profile.dto.ProfileDTO;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.List;

@Stateless
public class PdfExportService {

    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

    /**
     * Add all the fields of a profile in a document
     *
     * @param profileDTOList
     * @param document
     * @throws BusinessException
     */
    public void createPdf(List<ProfileDTO> profileDTOList, Document document) throws BusinessException {

        document.open();
        for (ProfileDTO profileDTO : profileDTOList) {
            addContent(document, profileDTO);
        }
        document.close();

    }

    public void createSinglePdf(ProfileDTO profileDTO, Document document) throws BusinessException {
        document.open();
        addContent(document, profileDTO);
        document.close();

    }

    private void addContent(Document document, ProfileDTO profileDTO) throws BusinessException {
        document.addTitle("Profile");
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Profile " + profileDTO.getEmail(), catFont));
        preface.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(preface, 2);
        try {
            document.add(preface);
        } catch (DocumentException e) {
            throw new BusinessException(ExceptionCode.PROFILE_NOT_EXPORTED);
        }
        createTable(document, profileDTO);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void createTable(Document document, ProfileDTO profileDTO) throws BusinessException {
        PdfPTable table = new PdfPTable(new float[]{25, 45});
        table.setWidthPercentage(60);

        table.addCell(getCell("First Name", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
        table.addCell(getCell(profileDTO.getFirstName(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        table.addCell(getCell("Last Name", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
        table.addCell(getCell(profileDTO.getLastName(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));

        if (profileDTO.getPhoto() != null && !profileDTO.getPhoto().equals("")) {
            String base64String = profileDTO.getPhoto();
            base64String = base64String.replaceFirst("data.*,", "");
            byte[] bytes = Base64.decode(base64String);
            PdfPCell cell;
            try {
                Image image = Image.getInstance(bytes);
                image.scaleAbsolute(202f, 200f);
                cell = new PdfPCell(image);
            } catch (IOException | BadElementException e) {
                throw new BusinessException(ExceptionCode.PROFILE_NOT_EXPORTED);
            }
            table.addCell(getCell("Photo", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
            table.addCell(cell);
        }
        if (profileDTO.getRegion() != null) {
            table.addCell(getCell("Region", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
            table.addCell(getCell(profileDTO.getRegion().getName(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        }

        if (profileDTO.getJobTitle() != null) {
            table.addCell(getCell("Job Title", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
            table.addCell(getCell(profileDTO.getJobTitle().getName(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
        }

        if (!profileDTO.getSkills().isEmpty()) {
            table.addCell(getCell("Skills", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
            table.addCell(getCell(profileDTO.skillEntryToString(0), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));

            for (int i = 1; i < profileDTO.getSkills().size(); i++) {
                table.addCell(getCell("", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
                table.addCell(getCell(profileDTO.skillEntryToString(i), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));
            }
        }

        if (!profileDTO.getProjekts().isEmpty()) {
            table.addCell(getCell("Projects", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
            table.addCell(getCell(profileDTO.getProjekts().get(0).getName(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));

            for (int i = 1; i < profileDTO.getProjekts().size(); i++) {
                table.addCell(getCell("", PdfPCell.ALIGN_CENTER, new BaseColor(0, 102, 204)));
                table.addCell(getCell(profileDTO.getProjekts().get(i).getName() + "\t" +
                        profileDTO.getProjekts().get(i).getBranch() + "\t" +
                        profileDTO.getProjekts().get(i).getClient() + "\t" +
                        profileDTO.getProjekts().get(i).getDescription(), PdfPCell.ALIGN_LEFT, BaseColor.WHITE));

            }
        }


        try {
            document.add(table);
        } catch (DocumentException e) {
            throw new BusinessException(ExceptionCode.PROFILE_NOT_EXPORTED);
        }

    }

    public PdfPCell getCell(String text, int alignment, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setFixedHeight(26f);
        cell.setPaddingBottom(11f);
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

}
