package com.hs.lib.inject;


public interface  AbstractInjector<T> {

	public Object inject(InjectType type, T target, Object source,boolean add);
}
