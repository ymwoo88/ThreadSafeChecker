# ThreadSafeChecker
스레드에 대한 테스트 코드를 검증하는 프로젝트

업무 중 코드리뷰에서 질문을 받은게 있었다  
static 메소드 내에 지역변수는 안전한가요?  
순간 벙쪗다 그럴거 같은데 잘은 모르겠다는거다... 확신이 없었는데 랩장님이 예제코드를 가지고 이해주켜주셨다

이 코드는 내가 다시 생각해서 짜낸 코드이다

본론으로 들어가면  
일부 프로젝트에서는 proxy 성으로 단순 전달만 하는 역활이 있어서 HttpServletRequest 객채의 getServletPath() 메소드를 사용하여 path를 바로 사용하는 구성을 했었다  
해당 객체는 컨트롤러에서 사용해서 service단으로 String path를 넘겼는데 코드리뷰에서 지적이 나왔었다  
그래서 공통 restTemplate 관련 클래스를 상속받아서 각 url별로 ApiClient를 사용하는 구성이 있는데  
여기에다 static메소드로 getServletPath를 호출하는 기능을 구현했던 거였다.

해당 메소드에서는 HttpServletRequest를 할당하는 구간이 있는데 여기서 또 의문이 들었다  
저 객체도 동시성 이슈가 없을가?

그래서 HttpServletRequest 검색해보니 빈 스코프 전략이 session scope였다  
즉 api call이 들어왔을 때 servletPath를 얻는 과정에서는 세션별로 빈 전략을 가지고 있기 때문에 내가 사용하려는 용도로 안전한 객체였다.  

그러면 두번째로 static메소드 내에 로컬 변수를 안전한가?  
예제코드 설명으로 이해해봅시다.  

랜덤 숫자를 밷는 static메소드를 1개 만들었다.    
이 메소드에서는 랜검숫자 생성 후 Thread.sleep(3000) 3초주었다
그리고 Thread를 사이로 전 후 로그를 찍게하였다  

그리고 

main함수에서 랜덤 숫자를 for 5번 호출하고 1초 간격으로 멀티 Thread를 호출하였다 

즉 호출 간격은 1초이고, 결과는 3초뒤에 나오는 테스트였다  
로그를보면 Thread ID별로 전 후 숫자가 찍혀있는데 동시간대에 호출 된 다른 Thread와 숫자 값이 다르다는 것을 확인 할 수 있었다.  
결론은 static 메소드 내에 지역변수는 Thread safety 하다라고 말할 수 있겠다
```
> Task :ThreadSafeCheckerApplication.main()
슬립 전 ThreadID[13] result >> 824
슬립 전 ThreadID[14] result >> 515
슬립 전 ThreadID[15] result >> 517
슬립 전 ThreadID[16] result >> 696
슬립 후 ThreadID[13] result >> 824
슬립 후 ThreadID[14] result >> 515
슬립 전 ThreadID[17] result >> 755
슬립 후 ThreadID[15] result >> 517
슬립 후 ThreadID[16] result >> 696
슬립 후 ThreadID[17] result >> 755
```