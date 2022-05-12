package persistence;

import model.Error;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ErrorRepository {

    public List<Error> getAllErrors() throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        List<Error> errors = new ArrayList<Error>();
        Statement smt = con.createStatement();
        String sql = "select distinct d.bewnr, l.anwender, \n" +
                "       l.dat_key_num1 as bestellnummer,\n" +
                "       l.dat_key_num3 as positionsnummer,\n" +
                "       datum_um(l.datum) as datum, \n" +
                "       l.werk,\n" +
                "       d.e_mail\n" +
                "from PSIPOS3.psidruauftrag d\n" +
                "        inner join (select * from ppad.pdrl where objekt = 'PBDR' union select * from ppabe.pdrl where objekt = 'PBDR' union select * from ppahu.pdrl where objekt = 'PBDR' union select * from pparo.pdrl where objekt = 'PBDR' union select * from ppatk.pdrl where objekt = 'PBDR' union select * from ppacn.pdrl where objekt = 'PBDR') l on l.listen_id = REGEXP_REPLACE(substr(d.parameter, instr(parameter, '@', 1, 1) + 1, instr(parameter, '@', 1, 2) - instr(parameter, '@', 1, 1) - 1), '^0+', '')\n" +
                "           and l.dat_key_num1 = substr(d.parameter, instr(parameter, '@', 1, 4) + 5, 6)" +
                "left join pcor83.pdis d on l.anwender = d.anwender\n" +
                "where errormsg = 'No ok message received by server' and d.macro = 'pbdr_be'";
        ResultSet rs = smt.executeQuery(sql);
        while(rs.next()) {
            Error error = new Error();
            error.setAnwender(rs.getString("anwender"));
            error.setBestellnummer(rs.getString("bestellnummer"));
            error.setDatum(rs.getString("datum"));
            error.setMail(rs.getString("e_mail"));
            error.setPositionsnummer(rs.getString("positionsnummer"));
            error.setWerk(rs.getString("werk"));
            error.setId(rs.getString("bewnr"));
            errors.add(error);
        }
        rs.close();
        smt.close();
        pool.releaseConnection();
        return errors;
    }

    public void updateErrorMessage(List<Error> list, String newMessage) throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        Statement smt = con.createStatement();
        for (Error e : list) {
            String sql = "update PSIPOS3.psidruauftrag " +
                    "     set errormsg = '" + newMessage + "'" +
                    "     where bewnr = " + e.getId();
            smt.execute(sql);
        }
        smt.close();
        con.close();
        pool.releaseConnection();
    }
}
