<template>
  <div class="content pa-0 ma-0">
    <v-app id="navigation" left-fixed-sidebar top-toolbar sidebar-under-toolbar>

    <!-- slideout nav -->
    <v-navigation-drawer temporary clipped class="accent" v-model="showmenu">
      <v-list class="pa-0">
        <!--Dashboard-->
        <v-list-item>
          <v-list-tile ripple class="white">
            <v-icon class="primary--text pr-3">home</v-icon>
            <v-list-tile-title class="primary--text">
              <router-link to="/" tag="span" style="cursor:pointer;">Dashboard</router-link>
            </v-list-tile-title>
          </v-list-tile>
        </v-list-item>
        <!--//Dashboard-->
        <v-divider></v-divider>
        <!--ModuleNav-->
        <v-list-item v-for="module in modules" :value="module.active" :key="module.name">
          <v-list-tile ripple>
            <v-icon class="white--text pr-3">{{ module.icon }}</v-icon>
            <v-list-tile-title>
              <router-link :to="module.link" tag="span" style="cursor:pointer;" class="white--text">{{ module.name }}</router-link>
            </v-list-tile-title>
          </v-list-tile>
          <v-divider></v-divider>
        </v-list-item>
        <!--//ModuleNav-->
      </v-list>
    </v-navigation-drawer>
    <!-- //slideout nav -->

    <!--top nav-->
    <v-toolbar fixed class="white">
      <v-toolbar-side-icon @click.native.stop="showmenu = !showmenu"></v-toolbar-side-icon>
      <v-toolbar-title class="text--text">{{ appmodule }}</v-toolbar-title>
      <v-toolbar-items>

        <v-menu bottom left offset-y>
          <v-btn primary outline slot="activator" class="primary--text">Client</v-btn>
          <v-list>
            <v-list-item v-for="client in clients" :key="client.id">
              <v-list-tile>
                <v-list-tile-title>{{ client.name }}</v-list-tile-title>
              </v-list-tile>
            </v-list-item>
          </v-list>
        </v-menu>
        <v-btn router  to="/JobHistory" icon slot="activator" v-tooltip:left="{ html: 'Global Job History' }">
          <v-icon>history</v-icon>
        </v-btn>
        <v-menu bottom left offset-y>
          <v-btn icon slot="activator">
            <v-icon>account_circle</v-icon>
          </v-btn>
          <v-list>
            <v-list-item>
              <v-list-tile>
                <v-list-tile-title>
                  <router-link to="/Logout" style="color: #354052">Logout</router-link>
                </v-list-tile-title>
              </v-list-tile>
              <v-list-tile>
                <v-list-tile-title>
                  <router-link to="/Settings">Settings</router-link>
                </v-list-tile-title>
              </v-list-tile>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar-items>
    </v-toolbar>
    <!--//top nav-->
   <router-view></router-view>

    <!--footer-->
    <v-footer fixed class="pa-3 footer">
      <div>&copy; {{ new Date().getFullYear() }}</div>
    </v-footer>
    <!--//footer-->

  </v-app>
  </div>
</template>

<script>
  import home from './components/Home.vue'
  import sidenav from './components/SideNav.vue'
  import balorTabs from './components/balorTabs.vue'
  import { mapState } from 'vuex'

  export default {
    name: 'app',
    data () {
      return {
        showmenu: false,
        showBalor: 1,
        clients: [
          {id: 1, name: 'Wendy'},
          {id: 2, name: 'Hertz'}
        ],
        modules: [
          {name: 'Balor', active: true, icon: 'label', link: '/Balor'},
          {name: 'Quantile', active: false, icon: 'label', link: '/Quantile'},
          {name: 'Core Lifecycle', active: false, icon: 'label', link: '/Lifecycle'},
          {name: 'bRelevant', active: false, icon: 'label', link: '/bRelevant'},
          {name: 'History', active: false, icon: 'history', link: '/JobHistory'},
          {name: 'Settings', active: false, icon: 'settings', link: '/Settings'},
          {name: 'Help', active: false, icon: 'help', link: '/Help'},
          {name: 'Feedback', active: false, icon: 'feedback', link: '/Feedback'},
          {name: 'Logout', active: false, icon: 'exit_to_app', link: '/Logout'}
        ]
      }
    },
    computed: mapState({
      appmodule: 'appmodule'
    }),
    components: {
      home,
      sidenav,
      balorTabs
    }
  }
</script>

<style lang="stylus">
  @import './assets/stylus/main.styl';
  @import '../node_modules/nouislider/distribute/nouislider.min.css';

  .content {
    flex: 1 0 auto;
    height:100%;
    padding:0;
    margin:0;
    display:flex;
    flex-direction:column;
    background-color:#eff3f6;
  }
  .input-group__details{display: none !important;}

  @media (max-width: 550px) {
    body {font-size: 12px;}
  }
</style>
