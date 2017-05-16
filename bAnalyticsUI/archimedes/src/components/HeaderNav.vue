<template>
  <div id="headerTemp">
    <b-navbar toggleable fixed="top" style="background-color: #ffffff;">
      <div class="container">

          <b-link class="navbar-brand" to="#">
            <span>{{ moduleName }}</span>
          </b-link>

          <b-nav is-nav-bar class="ml-auto">
            <b-nav-item>Job History</b-nav-item>
            <b-nav-item>Need Help?</b-nav-item>

            <!-- Navbar dropdowns -->
            <b-nav-item-dropdown text="Client">
              <b-dropdown-item v-for="client in clients" :key="client.id">{{ client.name }}</b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item-dropdown right>

              <!-- Using text slot -->
              <template slot="text">
                <span style="font-weight: bold;">User</span>
              </template>

              <b-dropdown-item to="#">Profile</b-dropdown-item>
              <b-dropdown-item to="#">Signout</b-dropdown-item>
            </b-nav-item-dropdown>

          </b-nav>
      </div>
    </b-navbar>
  </div>

</template>

<script>

  export default{
    name: 'headerTemp',
    data () {
      return {
        moduleName: 'DECILE',
        clients: [
              {id: 1, name: 'Wendy'},
              {id: 2, name: 'Hertz'}
        ]
      }
    }
  }

</script>

<style lang="scss" scoped>
  @import "../../node_modules/bootstrap/scss/bootstrap.scss";
  @media (max-width: 900px) {
    headerTemp {
      font-size: $font-size-xs;
    }
  }


</style>
