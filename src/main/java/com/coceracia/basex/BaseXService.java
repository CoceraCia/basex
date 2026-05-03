package com.coceracia.basex;

import org.basex.api.client.ClientSession;

public class BaseXService {
    private static final String HOST = "localhost";
    private static final int PORT = 1984;
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final String DB_NAME = "AutoresDB";

    private static final String AUTHORS_XML = """
            <autores>
              <autor id='1'>
                <nombre>Ana Pérez</nombre>
                <nacionalidad>España</nacionalidad>
              </autor>
              <autor id='2'>
                <nombre>John Smith</nombre>
                <nacionalidad>Reino Unido</nacionalidad>
              </autor>
            </autores>
            """;

    // check connection to BaseX server
    public void connectCheck() throws Exception {
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD);) {
            System.out.println("Connected to BaseX.");
        }
    }

    // create AuthorsDB
    public void createDatabase() throws Exception {
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD);) {
            try {
                session.execute("DROP DB " + DB_NAME);
                System.out.println("Existing 'AutoresDB' removed.");
            } catch (Exception ignored) {
                System.out.println("'AutoresDB' did not exist. Creating it now.");
            }

            String xmlInline = AUTHORS_XML.replace("\n", "");
            String createCommand = "CREATE DB " + DB_NAME + " \"" + xmlInline + "\"";
            session.execute(createCommand);
            System.out.println("Database 'AutoresDB' created with initial XML.");
        }
    }

    // query Spanish authors from AuthorsDB
    public void queryAuthors() throws Exception {
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD);) {
            String spanishAuthorsQuery = "for $a in db:get('AutoresDB')/autores/autor[nacionalidad='España'] return $a/nombre/text()";
            String result = session.execute("XQUERY " + spanishAuthorsQuery);
            System.out.println("Spanish authors:\n" + result);
        }
    }

    // update AuthorsDB by adding a premios node to author id=1
    public void addPremios() throws Exception {
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD);) {
            String updateQuery = "insert node <premios>2</premios> into db:get('AutoresDB')/autores/autor[@id='1']";
            session.execute("XQUERY " + updateQuery);
            System.out.println("Update applied: <premios>2</premios> added to author id=1.");
        }
    }

    // show AuthorsDB after update
    public void showDatabase() throws Exception {
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD);) {
            String afterUpdateQuery = "for $a in db:get('AutoresDB')/autores/autor return <autor>{$a/nombre}{$a/nacionalidad}{$a/premios}</autor>";
            String result = session.execute("XQUERY " + afterUpdateQuery);
            System.out.println("Authors data:\n" + result);
        }
    }
}
