package Day14.util;

import Day14.repository.BankRepository;

import java.io.*;

public class FileManager {

    private static final String FILE_PATH = "Day14/data/bank.dat";

    public void save(BankRepository repository)
            throws IOException {

        File file = new File(FILE_PATH);

        File parentDirectory = file.getParentFile();

        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        try (ObjectOutputStream writer =
                     new ObjectOutputStream(
                             new BufferedOutputStream(
                                     new FileOutputStream(file)
                             )
                     )) {

            writer.writeObject(repository);
        }
    }

    public BankRepository load()
            throws IOException, ClassNotFoundException {

        try (ObjectInputStream reader =
                     new ObjectInputStream(
                             new BufferedInputStream(
                                     new FileInputStream(FILE_PATH)
                             )
                     )) {

            return (BankRepository) reader.readObject();
        }
    }
}