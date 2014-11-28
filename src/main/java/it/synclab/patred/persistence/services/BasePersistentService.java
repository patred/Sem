package it.synclab.patred.persistence.services;

import it.synclab.patred.annotations.NoTransactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePersistentService<T> {
	
	protected Session session;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	
	@SuppressWarnings({ "unchecked" })
	public List<T> getAll(Integer maxResult) {
		Class clazz = (Class) getClass().getGenericSuperclass();
		String fullname = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0].toString();
		String name = fullname.substring(fullname.lastIndexOf('.') + 1);
		Query query = session.getNamedQuery("getAll" + name);
		query.setMaxResults(maxResult);
		return (List<T>) query.list();
	}
	
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
		return session.createQuery(query).list();
	}
	
	@Override
	@NoTransactional
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
}