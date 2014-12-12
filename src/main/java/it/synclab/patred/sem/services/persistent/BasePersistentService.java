package it.synclab.patred.sem.services.persistent;

import it.synclab.patred.sem.annotations.NoTransactional;
import it.synclab.patred.sem.services.BaseService;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class BasePersistentService<T> extends BaseService {
	
	protected Session session;
	
	public BasePersistentService() {
	}
	
	@NoTransactional
	public void setSession(Session session) {
		this.session = session;
	}
	
	@NoTransactional
	public Session getSession() {
		return session;
	}
	
	public List<T> getAll() {
		return getAll(Integer.MAX_VALUE);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> getAll(Integer maxResult) {
		Class clazz = (Class) getClass().getGenericSuperclass();
		String fullname = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0].toString();
		String name = fullname.substring(fullname.lastIndexOf('.') + 1);
		Query query = session.getNamedQuery("getAll" + name);
		query.setMaxResults(maxResult);
		return (List<T>) query.list();
	}
	
	@SuppressWarnings("rawtypes")
	public void deleteAll() {
		Class clazz = (Class) getClass().getGenericSuperclass();
		String fullname = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0].toString();
		String name = fullname.substring(fullname.lastIndexOf('.') + 1);
		Query query = session.getNamedQuery("deleteAll" + name);
		query.executeUpdate();
	}
	
	public void delete(T object) {
		session.delete(object);
	}
	
	public void saveOrUpdate(T object) {
		session.saveOrUpdate(object);
	}
	
	public void save(T object) {
		session.save(object);
	}
	
	public void update(T object) {
		session.update(object);
	}
	
	public void merge(T object) {
		session.merge(object);
	}
	
	public void evict(T object) {
		session.evict(object);
	}
	
	public void flush() {
		session.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> executeHQLQuery(String query) {
		Query createQuery = session.createQuery(query);
		createQuery.setMaxResults(1000);
		return createQuery.list();
	}
	
	public int executeSQLQuery(String query) {
		return session.createSQLQuery(query).executeUpdate();
	}
	
	@Override
	@NoTransactional
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
}