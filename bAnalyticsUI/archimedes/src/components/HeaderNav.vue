<template>
  <div id="headernav">
    <v-toolbar fixed class="green">
      <v-toolbar-side-icon @click.native.stop="showmenu = !showmenu"></v-toolbar-side-icon>
      <v-toolbar-title>{{ moduleName }}</v-toolbar-title>
      <v-toolbar-items>
        <v-toolbar-item><router-link to="/JobHistory">Job History</router-link></v-toolbar-item>
        <v-toolbar-item>Need Help?</v-toolbar-item>
        <v-menu bottom>
          <v-btn primary slot="activator">Clients</v-btn>
          <v-list>
            <v-list-item v-for="client in clients" :key="client.id">
              <v-list-tile>
                <v-list-tile-title>{{ client.name }}</v-list-tile-title>
              </v-list-tile>
            </v-list-item>
          </v-list>
        </v-menu>
        <v-menu>
          <v-btn icon slot="activator">
            <v-icon>account_circle</v-icon>
          </v-btn>
          <v-list>
            <v-list-item>
              <v-list-tile>
                <v-list-tile-title>Logout</v-list-tile-title>
              </v-list-tile>
              <v-list-tile>
                <v-list-tile-title>Settings</v-list-tile-title>
              </v-list-tile>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar-items>
    </v-toolbar>
  </div>

</template>

<script>

  export default{
    name: 'headernav',
    data () {
      return {
        showmenu: true,
        moduleName: 'DECILE',
        clients: [
          {id: 1, name: 'Wendy'},
          {id: 2, name: 'Hertz'}
        ]
      }
    }
  }

</script>

<style scoped lang="scss">

</style>
