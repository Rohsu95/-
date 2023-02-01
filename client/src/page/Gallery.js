import { useState } from "react";
import styled from "styled-components";
import { MainBtn } from "../component/Button";

import SwiperComponent from "../component/Swiper/Swiper";

const Wrapper = styled.div`
  align-items: center;
  display: flex;
  flex-direction: column;
  margin: 80px auto 0 auto;
  max-width: 1336px;
  width: 100%;

  .floor {
    background-color: ${(props) => props.theme.colors.main};
    border-radius: 0 0 10px 10px;
    height: 40px;
  }

  .roof {
    background-color: ${(props) => props.theme.colors.main};
    border-radius: 10px 10px 0 0;
    height: 40px;
  }

  .w95p {
    width: 95%;
  }
`;
const PostContainer = styled.div`
  /* display: flex; */
  /* align-items: center; */
  /* justify-content: space-between; */
`;
const ImgContainer = styled.div`
  width: 120px;
  height: 120px;
  border: 1px solid grey;

  margin-top: 16px;
  margin-left: 16px;
`;
const FliterLaout = styled.div`
  display: flex;
  float: right;
  color: white;
  margin-top: 8px;
  margin-right: 12px;
`;
const Newest = styled.span`
  margin-right: 12px;
`;
const Liked = styled.span``;
const Source = styled.div`
  display: flex;
  margin-top: -121px;
  justify-content: right;
  margin-right: 20px;
`;
const PostLayout = styled.div`
  width: 320px;
  height: 200px;
  border: 3px solid #a0c3d2;
  border-radius: 10px;
  margin-bottom: 16px;
`;
const Submit = styled.button`
  width: 50px;
  height: 30px;
  border: 0px;
  border-radius: 10px;
  background-color: #a0c3d2;
  color: white;
  &:active {
    transform: scale(0.95);
    box-shadow: 3px 2px 22px 1px rgba(0, 0, 0, 0.24);
  }
`;
const SubmitLayout = styled.div`
  display: flex;
  margin-top: 115px;
  margin-right: 20px;
  justify-content: right;
`;
export default function Gallery() {
  const [dropDown, setDropDown] = useState(false);
  const post = () => {
    setDropDown(!dropDown);
  };
  return (
    <>
      <Wrapper>
        <div className="w95p">
          <PostContainer>
            <MainBtn
              style={{ marginBottom: "16px" }}
              text={"POST"}
              onclick={post}
            />
            {dropDown ? (
              <PostLayout>
                <ImgContainer></ImgContainer>
                <Source>
                  <input />
                </Source>
                <SubmitLayout>
                  <Submit>등록</Submit>
                </SubmitLayout>
              </PostLayout>
            ) : null}
          </PostContainer>
          <div className="roof">
            <FliterLaout>
              <Newest>최신순</Newest>
              <Liked>좋아요순</Liked>
            </FliterLaout>
          </div>
          <SwiperComponent />
          <div className="floor" />
        </div>
      </Wrapper>
    </>
  );
}