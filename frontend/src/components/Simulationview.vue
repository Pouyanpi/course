<template>
  <div>
    <h1>Simulation '{{ this.$route.query.sim }}'</h1>

    <div v-if="loading">Loading...</div>
    <h2>Ranking</h2>
    <table class="agentlist" v-if="!loadingRanking">
      <tr><td>Rank</td><td>Agent</td><td>Utility</td><td>Source</td><td>Version</td></tr>
      <tr v-for="(rank,index) in ranking">
        <td>{{index + 1}}</td>
        <td>{{`${rank.type}`}}</td>
	<td>{{`${rank.averageUtility}`}}</td>
	<td><a :href="`${rank.url}`">source</a></td>
        <td>{{`${rank.version}`}}</td>
      </tr>
    </table>
    <h2>Visualizations</h2>
    <ul class="linklist" v-if="!loading">
      <!-- <li>
        // TODO insert proper simulation link on github
        <a :href="simDescription" target="_blank">Description</a>
      </li> -->
      <li>
        <router-link :to="{name: 'trades', query: {sim: this.$route.query.sim, day: 0, selection: 'consumers,firms', step: 1}}">Trade</router-link>
      </li>
    </ul>     
  </div>
</template>

<script>
import config from '../config';

export default {
  name: 'simulationview',
  data() {
    return {
      loading: true,
      loadingRanking: true,
      simDescription: '',
      ranking: null,
    };
  },
  created() {
    // get simulation info
    fetch(
      `${config.apiURL}/info?sim=${this.$route.query.sim}`,
      config.xhrConfig,
    )
    .then(config.handleFetchErrors)
    .then(response => response.json())
    .then(
      (response) => {
        this.simDescription = response.name;
        this.loading = false;
      },
    )
    .catch(error => config.alertError(error));

    // get simulation ranking
    fetch(
      `${config.apiURL}/ranking?sim=${this.$route.query.sim}`,
      config.xhrConfig,
    )
    .then(config.handleFetchErrors)
    .then(response => response.json())
    .then(
      (response) => {
        this.ranking = response.list;
        this.loadingRanking = false;
      },
    )
    .catch(error => config.alertError(error));
  },
};
</script>

<style lang="sass">
@import '../assets/sass/vars'
@import '../assets/sass/mixins'

.agentlist
  padding: 0

  tr:nth-child(even)
    background-color: #f2f2f2

  th, td
    padding: 10px
    text-align: left

  li
    text-align: left

</style>

