import {defineStore} from "pinia";
import {computed, ref} from "vue";

export const useMemberStore = defineStore('member', () => {

  const token = ref(null);
  const username = ref(null);
  const uuid = ref(null);
  const authorities = ref([]);
  const adult = ref(null);

  const isLoggedIn = computed(() => !!token.value);

  function decodeJwtPayload(token) {

    try {
      const base64 = token.split('.')[1];
      const json = atob(base64);
      return JSON.parse(json);
    } catch (e) {
      console.error("JWT 解析錯誤ㄌ", e);
      return null;
    }

  }

  function setLogin(data) {

    token.value = data.token;
    const payload = decodeJwtPayload(data.token);

    username.value = payload.username;
    uuid.value = payload.sub;
    authorities.value = payload.authorities;
    adult.value = payload.adult;

    localStorage.setItem('auth', JSON.stringify(
        {
          token: data.token,
          username: payload.username,
          uuid: payload.sub,
          authorities: payload.authorities,
          adult: payload.adult
        }
      )
    )

  }

  function logout() {

    token.value = null;
    username.value = null;
    uuid.value = null;
    authorities.value = [];
    adult.value = null;
    localStorage.removeItem('auth');

  }

  function restoreLogin() {

    const saved = localStorage.getItem('auth');

    if (!saved) {
      return;
    }

    const parsed = JSON.parse(saved);
    const payload = decodeJwtPayload(parsed.token);

    if (!payload) {
      return;
    }

    const now = Math.floor(Date.now() / 1000);
    if (payload.exp && payload.exp < now) {
      console.warn("JWT Expired");
      logout();
      return;
    }

    token.value = parsed.token;
    username.value = payload.username;
    uuid.value = payload.sub;
    authorities.value = payload.authorities;
    adult.value = payload.adult;

  }

  return {
    token,
    username,
    uuid,
    authorities,
    adult,
    isLoggedIn,
    setLogin,
    logout,
    restoreLogin
  }

});
