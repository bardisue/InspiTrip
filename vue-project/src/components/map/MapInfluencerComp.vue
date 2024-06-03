<script setup>
const props = defineProps({
  item: Object,
});

const getIframe = () => {
  let match = props.item.url.match(
    /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/(?:[^\/\n\s]+\/\S+\/|(?:v|e(?:mbed)?)\/|\S*?[?&]v=)|youtu\.be\/)([a-zA-Z0-9_-]{11})/
  );
  let videoId = match && match[1];
  return "https://www.youtube.com/embed/" + videoId;
};
</script>

<template>
  <div class="list-group">
    <a
      href="#"
      class="list-group-item list-group-item-action d-flex gap-3 py-3"
      aria-current="true"
    >
      <div class="d-flex flex-column gap-2 w-100 justify-content-between">
        <div>
          <img
            :src="item.iimg"
            alt="twbs"
            width="32"
            height="32"
            class="rounded-circle flex-shrink-0"
          />
          {{ item.iname }}
        </div>
        <div>
          <iframe
            width="560"
            height="315"
            :src="getIframe()"
            frameborder="0"
            allowfullscreen
          ></iframe>
        </div>
      </div>
    </a>
  </div>
</template>

<style scoped></style>
