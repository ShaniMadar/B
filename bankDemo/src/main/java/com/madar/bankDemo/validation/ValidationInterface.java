package com.madar.bankDemo.validation;

import org.springframework.stereotype.Service;

/**
 * @author madar
 *
 *Interface for validating the existence of provided id's in the database.
 *
 */
@Service
public interface ValidationInterface{
	boolean isValidInsert(long id, long parent_id);
	boolean isValidUpdate(long id, long parent_id);
	boolean isExist(long id);
	boolean isParentExist(long id);
}
