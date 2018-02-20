<template>
    <v-navigation-drawer
        ref="helpNav"
        class="pb-0 success"
        right
        temporary
        v-model="showmenuRight"
        height="100%"
        light
    >
      <v-list dense>
        <template v-for="(item, i) in items">
          <v-layout
              row
              v-if="item.heading"
              align-center
              :key="i"
          >
            <v-flex xs6>
              <v-subheader v-if="item.heading">
                {{ item.heading }}
              </v-subheader>
            </v-flex>
            <v-flex xs6 class="text-xs-center">
              <a href="#!" class="body-2 black--text">EDIT</a>
            </v-flex>
          </v-layout>
          <v-list-group v-else-if="item.children" v-model="item.model" no-action>
            <v-list-item slot="item">
              <v-list-tile>
                <v-list-tile-action>
                  <v-icon>{{ item.model ? item.icon : item['icon-alt'] }}</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                  <v-list-tile-title>
                    {{ item.text }}
                  </v-list-tile-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-list-item>
            <v-list-item
                v-for="(child, i) in item.children"
                :key="i"
            >
              <v-list-tile>
                <v-list-tile-action v-if="child.icon">
                  <v-icon>{{ child.icon }}</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                  <v-list-tile-title>
                    {{ child.text }}
                  </v-list-tile-title>
                </v-list-tile-content>
              </v-list-tile>
            </v-list-item>
          </v-list-group>
          <v-list-item v-else>
            <v-list-tile>
              <v-list-tile-action>
                <v-icon>{{ item.icon }}</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>
                  {{ item.text }}
                </v-list-tile-title>
              </v-list-tile-content>
            </v-list-tile>
          </v-list-item>
        </template>
      </v-list>
    </v-navigation-drawer>


    <!-- +++ right_drawer +++
    <v-navigation-drawer right temporary class="success" v-model="showmenuRight">
        <v-list dense>
          <v-list-tile-title class="text-xs-center">
            <h6 class="white--text text-xs-center mb-0 pt-2 pb-3">Help</h6>
          </v-list-tile-title>
          <v-expansion-panel>
            <v-expansion-panel-content v-for="(item,i) in 5" :key="i">
              <div slot="header">Item</div>
              <v-card>
                <v-card-text class="grey lighten-3">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</v-card-text>
              </v-card>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-list>
      </v-navigation-drawer>-->
</template>

<script>
  export default {
    data: () => ({
      showmenuRight: false,
      items: [
        {icon: 'contacts', text: 'Contacts'},
        {icon: 'history', text: 'Frequently contacted'},
        {icon: 'content_copy', text: 'Duplicates'},
        {
          icon: 'keyboard_arrow_up',
          'icon-alt': 'keyboard_arrow_down',
          text: 'Labels',
          model: true,
          children: [
            {icon: 'add', text: 'Create label'}
          ]
        },
        {icon: 'settings', text: 'Settings'},
        {icon: 'chat_bubble', text: 'Send feedback'},
        {icon: 'help', text: 'Help'},
        {icon: 'phonelink', text: 'App downloads'},
        {icon: 'keyboard', text: 'Got to the old version'}
      ]
    })
  }
</script>

<style scoped>

</style>
