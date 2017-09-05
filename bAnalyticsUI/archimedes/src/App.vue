<template>
  <div class="content"><v-app id="navigation" left-fixed-sidebar top-toolbar sidebar-under-toolbar>

    <!-- slideout nav -->
    <v-navigation-drawer temporary clipped class="accent" v-model="showmenu">
      <v-list class="pa-0">
        <v-list-item>
          <v-list-tile ripple class="white">
            <v-icon class="primary--text pr-3">home</v-icon>
            <v-list-tile-title class="primary--text">
              <router-link to="/">Dashboard</router-link>
            </v-list-tile-title>
          </v-list-tile>
        </v-list-item>
        <v-divider></v-divider>
        <v-list-item v-for="module in modules" :value="module.active" :key="module.name">
          <v-list-tile ripple>
            <v-icon class="white--text pr-3">{{ module.icon }}</v-icon>
            <v-list-tile-title>{{ module.name }}</v-list-tile-title>
          </v-list-tile>
          <v-divider></v-divider>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <!-- //slideout nav -->

    <!--top nav-->
    <v-toolbar fixed class="white">
      <v-toolbar-side-icon @click.native.stop="showmenu = !showmenu"></v-toolbar-side-icon>
      <v-toolbar-title class="text--text">{{ appmodule }}</v-toolbar-title>
      <v-toolbar-items>
        <v-menu>
          <v-btn primary slot="activator" class="white--text">Client</v-btn>
          <v-list>
            <v-list-item v-for="client in clients" :key="client.id">
              <v-list-tile>
                <v-list-tile-title>{{ client.name }}</v-list-tile-title>
              </v-list-tile>
            </v-list-item>
          </v-list>
        </v-menu>
        <v-btn icon slot="activator" v-tooltip:left="{ html: 'Global Job History' }">
          <v-icon>history</v-icon>
        </v-btn>
        <v-menu>
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

    <!--main container-->
    <main class="pt-5">
      <v-container fluid>
        <v-layout row-sm column child-flex-sm>
          <v-flex md2>
            <modulenav></modulenav>
          </v-flex>
          <v-flex xs10>
            <div>
              <!--<p>first line</p>
              <p>we can put notices here and they will be always visible from everywhere in the app..</p>-->
              <router-view></router-view>
            </div>
          </v-flex>
        </v-layout>
      </v-container>
    </main>
    <!--//main container-->

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
  import modulenav from './components/ModuleNav.vue'
  import headernav from './components/HeaderNav.vue'
  import sidenav from './components/SideNav.vue'
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
          {name: 'Balor', active: true, icon: 'label'},
          {name: 'Decile', active: false, icon: 'label'},
          {name: 'bRelevant', active: false, icon: 'label'},
          {name: 'History', active: false, icon: 'history'},
          {name: 'Settings', active: false, icon: 'settings'},
          {name: 'Help', active: false, icon: 'help'},
          {name: 'Feedback', active: false, icon: 'feedback'},
          {name: 'Logout', active: false, icon: 'exit_to_app'}
        ]
      }
    },
    computed: mapState({
      appmodule: 'appmodule'
    }),
    components: {
      home,
      headernav,
      modulenav,
      sidenav
    }
  }
</script>

<style lang="stylus">
  @import './assets/stylus/main.styl';

  .content{flex: 1 0 auto;height:100%;padding:0;margin:0;display:flex;flex-direction:column;}

  .footer{
    height:80px;
  }
  .ftr_logo{
    height:47px;
  }

  @media (max-width: 550px) {
    body {font-size: 12px;}
  }
</style>
