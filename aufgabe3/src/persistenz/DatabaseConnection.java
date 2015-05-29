package persistenz;

import utilities.NotFoundException;
import utilities.TechnicalException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DatabaseConnection implements IPersistenzService {

    public final static String ANGEBOT = "angebot";
    public final static String AUFTRAG = "auftrag";
    public final static String BAUTEIL = "bauteil";
    public final static String FERTIGUNGSAUFTRAG = "fertigungsauftrag";
    public final static String FERTIGUNGSPLAN = "fertigungsplan";

    ConcurrentMap<String, ConcurrentMap<Integer, String>> db;
    ConcurrentMap<String, Integer> maxKey = new ConcurrentHashMap<String, Integer>();

	public DatabaseConnection() throws TechnicalException {
        db = new ConcurrentHashMap<String, ConcurrentMap<Integer,String>>();
        db.putIfAbsent(ANGEBOT, new ConcurrentHashMap<Integer, String>());
        maxKey.put(ANGEBOT, 0);
        db.putIfAbsent(AUFTRAG, new ConcurrentHashMap<Integer, String>());
        maxKey.put(AUFTRAG, 0);
        db.putIfAbsent(BAUTEIL, new ConcurrentHashMap<Integer, String>());
        maxKey.put(BAUTEIL, 0);
        db.putIfAbsent(FERTIGUNGSAUFTRAG, new ConcurrentHashMap<Integer, String>());
        maxKey.put(FERTIGUNGSAUFTRAG, 0);
        db.putIfAbsent(FERTIGUNGSPLAN, new ConcurrentHashMap<Integer, String>());
        maxKey.put(FERTIGUNGSPLAN, 0);
    }

    @Override
    synchronized public int create(String type, String typeRep) throws TechnicalException {
        Integer id = maxKey.get(type);
        db.get(type).put(id, typeRep);
        maxKey.put(type, maxKey.get(type)+1);
        return id.intValue();
    }

    @Override
    synchronized public String read(String type, int id) throws NotFoundException, TechnicalException {
        Integer i = Integer.valueOf(id);
        if (!db.get(type).containsKey(i)) {
            NotFoundException.throwNotFoundException();
        }
        return db.get(type).get(i);
    }

    @Override
    synchronized public void update(String type, int id, String typeRep) throws NotFoundException, TechnicalException {
        read(type, id);
        db.get(type).put(Integer.valueOf(id), typeRep);
    }

    @Override
    synchronized public void delete(String type, int id) throws TechnicalException {
        db.get(type).remove(Integer.valueOf(id));
    }
}