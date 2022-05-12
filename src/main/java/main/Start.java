package main;

import persistence.ErrorRepository;
import service.ExcelCreatorService;
import service.ListSplitService;
import service.LogService;
import model.Error;
import service.MailService;

import java.io.File;
import java.util.List;

public class Start {

    public static void main(String[] args) {
        try {
            ErrorRepository rep = new ErrorRepository();
            List<Error> errorList = rep.getAllErrors();
            if (errorList.size() > 0) {
                // log all errors
                String message = "Gefundene Fehler:\n";
                for (Error e : errorList) {
                    message += e.getAnwender() + "|" + e.getBestellnummer() + "|" + e.getPositionsnummer() + "|" + e.getDatum() + "|" + e.getWerk() + "|" + e.getMail() + "\n";
                }
                LogService.log(message);
                // split lists based on email address
                List<List<Error>> lists = ListSplitService.splitList(errorList);
                ExcelCreatorService excelService = new ExcelCreatorService();
                MailService mailService = new MailService();
                for(List<Error> l : lists) {
                    // create file
                    File file = excelService.createExcelFile(l);
                    // send mail; all mails in list should be equal
                    mailService.sendMail(l.get(0).getMail(), file);
                    LogService.log("Mail versandt an " + l.get(0).getMail());
                }
                // update error messages
                rep.updateErrorMessage(errorList, "No ok message received by server (benachrichtigt)");
            } else {
                LogService.log("Keine Fehler");
            }
        } catch (Exception e) {
            LogService.log(e);
            LogService.sendErrorMail(e);
        }
    }
}
