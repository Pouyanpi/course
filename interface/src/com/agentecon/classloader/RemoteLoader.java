/**
 * Created by Luzius Meisser on Jun 18, 2017
 * Copyright: Meisser Economics AG, Zurich
 * Contact: luzius@meissereconomics.com
 *
 * Feel free to reuse this code under the MIT License
 * https://opensource.org/licenses/MIT
 */
package com.agentecon.classloader;

import java.io.IOException;
import java.util.HashMap;

public abstract class RemoteLoader extends ClassLoader {

	private String version;
	protected SimulationHandle source;
	protected HashMap<SimulationHandle, RemoteLoader> subloaders;

	public RemoteLoader(ClassLoader parent, SimulationHandle source) throws IOException {
		super(parent);
		this.version = source.getVersion();
		this.source = source;
		this.subloaders = new HashMap<>();
	}

	public boolean usesRepository(String repo) {
		if (source.getRepo().equals(repo)) {
			return true;
		} else {
			for (SimulationHandle handle: subloaders.keySet()) {
				if (handle.getRepo().equals(repo)) {
					return true;
				}
			}
			return false;
		}
	}

	public void registerSubloader(SimulationHandle handle, RemoteLoader loader) {
		RemoteLoader prev = this.subloaders.put(handle, loader);
		assert prev == null;
	}

	public RemoteLoader getSubloader(SimulationHandle handle) {
		return this.subloaders.get(handle);
	}
	
	public String getVersionString() {
		return version;
	}

	public boolean isUptoDate() throws IOException {
		return source.getVersion().equals(version);
	}

	public String getOwner() {
		return source.getRepo();
	}

	public SimulationHandle getSource() {
		return source;
	}
	
	@Override
	public String toString() {
		return "Class loader that loads from " + source;
	}

}
