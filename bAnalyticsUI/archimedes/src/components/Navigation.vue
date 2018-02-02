<template>
  <div class="content pa-0 ma-0">
    <v-app id="navigation" left-fixed-sidebar top-toolbar sidebar-under-toolbar>

      <!-- =====slideout nav===== -->
      <v-navigation-drawer fill-height temporary clipped class="accent" v-model="showmenu">
        <v-list class="pa-0">
          <!--Dashboard-->
          <v-list-item>
            <v-list-tile ripple class="white">
              <v-icon class="primary--text pr-3">dashboard</v-icon>
              <v-list-tile-title class="primary--text">
                <router-link to="/Home" tag="span" style="cursor:pointer;">Dashboard</router-link>
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
      <!-- //=====slideout nav===== -->

      <!-- =====top nav===== -->
      <v-toolbar fixed class="white">
        <v-toolbar-side-icon @click.native.stop="showmenu = !showmenu"></v-toolbar-side-icon>
        <v-toolbar-title class="text--text">{{ appmodule }}</v-toolbar-title>
        <v-toolbar-items>
          <v-select
              v-bind:items="clients"
              v-model="activeClient"
              label="Select Client"
              single-line
              auto
              append-icon="arrow_drop_down"
              hide-details
              style="width:200px;"
              class="input-group--focused custom_input"
              v-tooltip:bottom="{ html: 'Select Client' }"
            ></v-select>
          <v-btn
              router
              to="/JobHistory"
              icon slot="activator"
              class="accent--text"
              v-tooltip:bottom="{ html: 'Global Job History' }">
                <v-icon x-large>history</v-icon>
          </v-btn>
          <v-menu bottom left offset-y>
            <v-btn
                icon slot="activator"
                v-tooltip:bottom="{ html: 'Profile' }">
              <v-icon class="accent--text">account_circle</v-icon>
            </v-btn>
            <v-list class="pt-0" style="width:300px;">
              <v-icon class="accent--text drop_arrow2">arrow_drop_up</v-icon>
              <v-list-item v-for="item in userInfo" v-bind:key="item.LastName">
              <!-- User Info -->
              <div class="pt-2 pb-2 success">
                  <v-list-tile avatar class="pl-0 pr-0">
                    <div class="ml-1 mr-1" style="height:100%;">
                      <v-list-tile-avatar class="bg_avatar" :class="[item.iconClass]">
                        <h6 class="mb-0 white--text" style="margin:0 auto;">JF</h6>
                        <!--<img src="/static/bp_logo_reverse_color2.png" />-->
                      </v-list-tile-avatar>
                    </div>
                    <v-list-tile-content class="ml-1">
                      <v-list-tile-title class="white--text">{{item.firstName}} {{item.lastName}}</v-list-tile-title>
                      <v-list-tile-sub-title class="white--text">{{item.email}}</v-list-tile-sub-title>
                    </v-list-tile-content>
                  </v-list-tile>
              </div>
            </v-list-item>
              <!-- //User Info -->
              <v-list-item v-for="item in profile" v-bind:key="item.name" class="drop_item">
                <v-list-tile>
                  <v-list-tile-title>
                    <v-icon class="pr-2">{{item.icon}}</v-icon>
                    <a :href="item.link" class="body-2">{{ item.name }}</a>
                  </v-list-tile-title>
                </v-list-tile>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-toolbar-items>
      </v-toolbar>
      <!-- //=====top nav===== -->

      <!-- =====router===== -->
      <router-view></router-view>
      <!-- //=====router===== -->

      <!-- =====footer===== -->
      <v-footer fixed class="pa-3 footer">
        <a href="http://www.brierley.com/" target="_blank"><img src="/static/bp_logo_reverse_color2.png" class="logo" /></a>
        <div>All Rights Reserved &copy; {{ new Date().getFullYear() }}</div>
      </v-footer>
      <!-- //=====footer===== -->

    </v-app>
  </div>
</template>

<script>
  import home from './Home.vue'
  import { mapState } from 'vuex'

  export default {
    name: 'nav',
    data () {
      return {
        activeClient: null,
        showmenu: false,
        showBalor: 1,
        clients: [
          {text: 'Wendy\'s', icon: 'account_box', link: ''},
          {text: 'Hertz', icon: 'account_circle', link: ''}
        ],
        modules: [
          {name: 'BALOR', active: true, icon: 'label', link: '/Balor'},
          {name: 'Pareto', active: false, icon: 'label', link: '/Pareto'},
          {name: 'Core Lifecycle', active: false, icon: 'label', link: '/Lifecycle'},
          {name: 'b-Relevant', active: false, icon: 'label', link: '/bRelevant'},
          {name: 'History', active: false, icon: 'history', link: '/JobHistory'},
          {name: 'Settings', active: false, icon: 'settings', link: '/Settings'},
          {name: 'Help', active: false, icon: 'help', link: '/Help'},
          {name: 'Feedback', active: false, icon: 'feedback', link: '/Feedback'},
          {name: 'Logout', active: false, icon: 'exit_to_app', link: '/Logout'}
        ],
        profile: [
          {name: 'Profile', icon: 'person', link: '/Home'},
          {name: 'Settings', icon: 'settings', link: '/Settings'},
          {name: 'Feeback', icon: 'feedback', link: '/Feedback'},
          {name: 'Help', icon: 'help', link: '/Help'},
          {name: 'Logout', icon: 'exit_to_app', link: '/Logout'}
        ],
        userInfo: [
          {firstName: 'FirstName', lastName: 'LastName', email: 'someone@juju.com', iconClass: 'info'}
        ]
      }
    },
    computed: mapState({
      appmodule: 'appmodule'
    }),
    components: {
      home
    }
  }
</script>

<style lang="stylus">
  #navigation {height: 100%;}
  .content {
    flex: 1 0 auto;
    height:100%;
    padding:0;
    margin:0;
    display:flex;
    flex-direction:column;
    background-color:#eff3f6;
  }
  .padR6 {padding-right:6px;}
  .footer {border-top:2px solid #00313d;height:55px;}
  .footer .logo {height:45px;}
  .application--footer-fixed.application--toolbar > aside.navigation-drawer.navigation-drawer--clipped  {max-height:calc(100vh - 55px) !important;}

  .bg_avatar {
    border-radius: 30px;
    border:1px solid #000000;
  }
  .drop_arrow2{
    position:relative;
    left:270px;
  }
  @media (max-width: 550px) {
    body {font-size: 12px;}
  }
</style>
