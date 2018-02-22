<template>
  <router-view></router-view>
</template>

<script>
  export default {
    name: 'app'
  }
</script>

<style lang="stylus">
  @import './assets/stylus/main.styl';
  @import '../node_modules/nouislider/distribute/nouislider.min.css';

  /* truncates dropdowns text on multi-selects */
  .input-group--text-field.input-group--dirty:not(.input-group--textarea),
  .input-group--text-field:not(.input-group--single-line).input-group--focused:not(.input-group--textarea),
  .input-group--text-field:not(.input-group--single-line):focus:not(.input-group--textarea) {
    text-overflow: ellipsis;
    display: -webkit-box;
    /* -webkit-line-clamp: 1; */
    -webkit-box-orient: vertical;
    max-height: 36px;
  }
  .custom_input {
    border-radius:4px;
    background-color:#F5F8FA !important;
    border:1px solid #DFE3E9 !important;
  }
  html {background-color:#eff3f6;}
  .icon_help {line-height: 20px !important;}
</style>
