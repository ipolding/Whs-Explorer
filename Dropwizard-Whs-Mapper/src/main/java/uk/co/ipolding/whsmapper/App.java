package uk.co.ipolding.whsmapper;


import org.skife.jdbi.v2.DBI;
import org.w3c.dom.*;
import uk.co.ipolding.whsmapper.jdbi.WhsDao;
import uk.co.ipolding.whsmapper.core.WorldHeritageSite;
import uk.co.ipolding.whsmapper.xmlparser.WorldHeritageSiteParser;

import java.util.List;

import static uk.co.ipolding.whsmapper.DatabaseConfiguration.getH2DatabaseInstance;

public class App
{

    public static void main( String[] args ) throws Exception {
        System.out.println("Hello World!");

        WorldHeritageSiteParser parser = new WorldHeritageSiteParser();
        Document documentWhs = null;
        try {
            documentWhs = parser.getWhsDocument();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

        List<WorldHeritageSite> siteList = parser.getWorldHeritageSite(documentWhs);

        DBI dbi = getH2DatabaseInstance();
        WhsDao dao = dbi.open(WhsDao.class);

        dao.createSiteTable();

        for (WorldHeritageSite site : siteList) {
            if (dao.findNameById(site.getId())==null)
                dao.insert(site.getId(), site.getName(), site.getDescription(), site.getLatitude(), site.getLongitude());
//            String name = dao.findNameById(1);
//            System.out.print(name);
        }
        dao.close();
    }
}