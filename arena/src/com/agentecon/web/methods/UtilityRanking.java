/**
 * Created by Luzius Meisser on Jun 18, 2017
 * Copyright: Meisser Economics AG, Zurich
 * Contact: luzius@meissereconomics.com
 *
 * Feel free to reuse this code under the MIT License
 * https://opensource.org/licenses/MIT
 */
package com.agentecon.web.methods;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.agentecon.agent.Agent;
import com.agentecon.agent.AgentRef;
import com.agentecon.agent.IAgent;
import com.agentecon.consumer.IConsumer;
import com.agentecon.consumer.IConsumerListener;
import com.agentecon.goods.Inventory;
import com.agentecon.sim.SimulationListenerAdapter;
import com.agentecon.util.IAverage;
import com.agentecon.util.MovingAverage;

public class UtilityRanking extends SimulationListenerAdapter {
	
	private ArrayList<ConsumerListener> list;

	public UtilityRanking() {
		this.list = new ArrayList<>();
	}

	@Override
	public void notifyConsumerCreated(IConsumer consumer) {
		ConsumerListener listener = new ConsumerListener((Agent) consumer);
		list.add(listener);
		consumer.addListener(listener);
	}

	public void print(PrintStream out) {
		Collections.sort(list);
		int rank = 1;
		System.out.println("Rank\tType\tId\tAvg Utility");
		for (ConsumerListener l : list) {
			out.println(rank++ + "\t" + l);
		}
	}
	
	public Collection<Rank> getRanking(){
		HashMap<String, Rank> ranking = new HashMap<String, Rank>();
		for (ConsumerListener listener: list){
			Rank rank = ranking.get(listener.getType());
			if (rank == null) {
				rank = new Rank(listener.getType(), listener.getAgent());
				ranking.put(listener.getType(), rank);
			}
			rank.add(listener.getAverage());
		}
		ArrayList<Rank> list = new ArrayList<>(ranking.values());
		Collections.sort(list);
		return list;
	}

	class ConsumerListener implements IConsumerListener, Comparable<ConsumerListener> {

		private AgentRef agent;
		private IAverage averageUtility;

		public ConsumerListener(IAgent agent) {
			this.agent = agent.getReference();
			this.averageUtility = new MovingAverage(0.98);
		}
		
		public Agent getAgent() {
			return (Agent) agent.get();
		}

		public String getType(){
			return getAgent().getType();
		}
		
		public double getAverage(){
			return averageUtility.getAverage();
		}

		@Override
		public void notifyConsuming(IConsumer inst, int age, Inventory inv, double utility) {
			averageUtility.add(utility);
		}

		@Override
		public void notifyRetiring(IConsumer inst, int age) {
		}

		@Override
		public void notifyInvested(IConsumer inst, double amount) {
		}

		@Override
		public void notifyDivested(IConsumer inst, double amount) {
		}

		@Override
		public int compareTo(ConsumerListener o) {
			return o.averageUtility.compareTo(averageUtility);
		}

		@Override
		public String toString() {
			IAgent agent = this.agent.get();
			return agent.getType() + "\t" + agent.getAgentId() + "\t" + averageUtility.getAverage();
		}

	}
	
}


