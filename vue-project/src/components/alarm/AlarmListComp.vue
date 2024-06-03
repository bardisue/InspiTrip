<script setup>
import { useMemberStore } from '@/stores/member';
import AlarmContent from '@/components/alarm/AlarmContentComp.vue'

const member = useMemberStore();

</script>

<template>
  <div class="container mt-5">
    <ul class="nav nav-tabs" id="myTab" role="tablist">
      <li class="nav-item" role="presentation">
        <button
          class="nav-link active"
          id="received-tab"
          data-bs-toggle="tab"
          data-bs-target="#received"
          type="button"
          role="tab"
          aria-controls="received"
          aria-selected="true"
        >
          받은알림함
        </button>
      </li>
    </ul>
    <div class="tab-content" id="myTabContent">
      <div
        class="tab-pane fade show active"
        id="received"
        role="tabpanel"
        aria-labelledby="received-tab"
      >
      <AlarmContent v-for="item in member.alarms" :key="item.no" :item="item"></AlarmContent>
        <!-- 보낸 알림함 내용 -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.notification-item {
  display: flex;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #dee2e6;
}
.notification-item img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
.notification-item .content {
  flex-grow: 1;
  padding-left: 10px;
}
.notification-item .timestamp {
  white-space: nowrap;
}
.nav-tabs .nav-link.active {
  color: #495057;
  background-color: #e9ecef;
}
</style>
