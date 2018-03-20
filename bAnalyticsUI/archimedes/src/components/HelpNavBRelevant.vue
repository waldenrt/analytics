<template>
  <!-- +++ right_drawer +++-->
    <v-navigation-drawer ref="helpNav" right temporary class="white" v-model="showmenuRight">
      <v-card-title primary-title class="primary pa-2">
        <h6 class="white--text text-xs-left mb-0">b-Relevant - Help</h6>
      </v-card-title>

      <!-- Page1_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Page1</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpSummaryItems" :key="i">
            <summary class="grey--text lighten-2 subheading"><span class="accent--text">{{item.title}}</span></summary>
            <p class="pt-1 pl-3">
              <span>{{item.text}}</span><br />
              <span>
                <a :href="item.link" target="_blank">read more</a>
                <a :href="item.link" target="_blank" style="text-decoration:none;">
                  <v-icon style="font-size:14px;">launch</v-icon>
                </a>
              </span>
            </p>
        </details>
      </v-container>
      <v-divider></v-divider>

      <!-- Page1_help -->
      <p class="body-2 grey lighten-2 info--text pa-2 ma-0">Page2</p>
      <v-container class="pa-2">
        <v-divider></v-divider>
        <details v-for="(item,i) in helpDetailsItems" :key="i">
            <summary class="grey--text lighten-2 subheading"><span class="accent--text">{{item.title}}</span></summary>
            <p class="pt-1 pl-3">
              <span>{{item.text}}</span><br />
              <span>
                <a :href="item.link" target="_blank">read more</a>
                <a :href="item.link" target="_blank" style="text-decoration:none;">
                  <v-icon style="font-size:14px;">launch</v-icon>
                </a>
              </span>
            </p>
        </details>
      </v-container>
    </v-navigation-drawer>
</template>

<script>
  export default {
    data: () => ({
      showmenuRight: false,
      helpSummaryItems: [
        {
          title: 'Section1',
          text: 'Some text some text some text some text some text.',
          link: '#/Help/#bRelevant1'
        }
      ],
      helpDetailsItems: [
        {
          title: 'Section1',
          text: 'Some text some text some text some text some text.',
          link: '#/Help/#bRelevant2'
        },
        {
          title: 'Section2',
          text: 'Some text some text some text some text some text.',
          link: '#/Help/#bRelevant3'
        },
        {
          title: 'Section3',
          text: 'Some text some text some text some text some text.',
          link: '#/Help/#bRelevant4'
        }
      ]
    })
  }
</script>

<style scoped>

</style>
